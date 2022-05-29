import { Formik, Form, FieldArray } from "formik"
import { IoIosRemoveCircle, IoMdAddCircle } from "react-icons/io"
import FormHandler from "@components/Form/Form.jsx"
import FormField from "@components/Form/FormField.jsx"
import useApi from "src/hooks/useApi"
import Button from "./Button"
import { useState, useEffect, useCallback } from "react"
import { v4 as uuidv4 } from "uuid"
import ColorPicker from "./ColorPicker"
import * as yup from "yup"
import ErrorBox from "@components/Misc/ErrorBox"
import { makeClient } from "@services/makeClient"
import axios from "axios"
import { images } from "next.config"

const initialValues = {
  label: "",
  category: "",
  description: "",
  items: [],
  length: 1,
  width: 1,
  height: 1,
  weight: 1,
}

const validationSchema = yup.object().shape({
  label: yup.string().required().label("label"),
  category: yup.string().required().label("category"),
  description: yup.string().required().label("description"),
  items: yup
    .array()
    .of(
      yup.object().shape({
        stock: yup.string(),
      })
    )
    .compact((v) => !v.checked),
})

const FormAddFurniture = () => {
  const [errCategory, dataCategory] = useApi("get", "/categories")
  const [errColor, dataColor] = useApi("get", "/colors")
  const [error, setError] = useState(null)

  function renameFile(originalFile, newName) {
    const type = "." + originalFile.type.split("/")[1]
    return new File([originalFile], newName + type, {
      type: originalFile.type,
      lastModified: originalFile.lastModified,
    })
  }

  const uploadToServer = async (imagesArray) => {
    const config = {
      headers: { "content-type": "multipart/form-data" },
      onUploadProgress: (event) => {
        console.log(
          `Current progress:`,
          Math.round((event.loaded * 100) / event.total)
        )
      },
    }

    const formData = new FormData()

    imagesArray.map((image) => {
      formData.append("files", image)
    })
    console.log(formData)
    await axios.post("/api/upload", formData, config)
  }

  const handleFormSubmit = useCallback(
    async ({
      label,
      description,
      category,
      height,
      length,
      width,
      weight,
      items,
    }) => {
      setError(null)
      const furniture = {
        label,
        description,
        dimension: {
          length,
          width,
          height,
        },
        weight,
        items,
        category: {
          id: parseInt(category),
        },
      }
      console.log(furniture)

      try {
        const { data } = await makeClient().post("/furnitures", furniture)
        const imageArray = []

        if (data) {
          Promise.all(
            Object.values(data.items).map(async (item, indexItem) => {
              Object.values(item.images).map((image, indexImage) => {
                let imageToUpload =
                  furniture.items[indexItem].images[indexImage]

                imageToUpload = renameFile(imageToUpload, image.name)

                imageArray.push(imageToUpload)
              })
            })
          )

          await uploadToServer(imageArray)
        }
      } catch (err) {
        setError(err.message)
        console.log({ err })
      }
    },
    []
  )

  return (
    <section>
      <h2 className="font-sans font-bold break-normal text-gray-700 px-2 pb-8 text-xl">
        Add furniture
      </h2>
      {error ? <ErrorBox message={error} /> : null}
      <Formik
        onSubmit={handleFormSubmit}
        initialValues={initialValues}
        validationSchema={validationSchema}
      >
        {({ handleSubmit, isValid, isSubmitting, values, setFieldValue }) => (
          <Form
            onSubmit={handleSubmit}
            noValidate
            className="shadow-md rounded p-10 mb-4 bg-white w-full"
          >
            <h3 className="text-center m-3 text-lg font-bold">
              About furniture
            </h3>
            <div className="flex justify-around w-full">
              <FormField
                name="label"
                label="Label"
                type="text"
                placeholder="Enter a label name for the furniture"
              />
              <FormField
                name="category"
                className="block appearance-none w-full bg-gray-200 border border-gray-200 text-gray-700 py-2 px-4 pr-8 rounded leading-tight focus:outline-none focus:bg-white focus:border-gray-500"
                label="Category"
                as="select"
              >
                <option value="DEFAULT" selected hidden>
                  Choose category
                </option>
                {Object.values(dataCategory).map((category) => (
                  <option value={category.id}>{category.label}</option>
                ))}
              </FormField>
            </div>
            <FormField
              name="description"
              label="Description content *"
              as="textarea"
              rows="5"
              className="form-control resize-none bg-gray-200 shadow-inner rounded-l p-2 flex-1 w-full"
              placeholder="Enter any content to describe the furniture"
            />
            <h3 className="text-center m-3 text-lg font-bold">
              Dimensions and weight
            </h3>
            <div className="flex justify-around w-full">
              <FormField
                name="length"
                label="Length"
                type="number"
                step="0.01"
              />
              <FormField name="width" label="Width" type="number" step="0.01" />
              <FormField
                name="height"
                label="Height"
                type="number"
                step="0.01"
              />
              <FormField
                name="weight"
                label="Weight"
                type="number"
                step="0.01"
              />
            </div>
            <FieldArray
              name="items"
              render={(arrayHelpers) => (
                <div>
                  <h3 className="text-center m-3 text-lg font-bold">
                    Colors available
                  </h3>
                  <ColorPicker
                    dataColor={dataColor}
                    itemsArray={arrayHelpers}
                    getIndex={(colorId) => {
                      return values.items.findIndex(
                        (item) => item.color.id === parseInt(colorId)
                      )
                    }}
                  />
                  <h3 className="text-center m-3 text-lg font-bold">Items</h3>
                  {values.items.map((items, index) => (
                    <div key={index} className="flex justify-between w-full">
                      <FormField
                        name={`items.${index}.stock`}
                        label={`Stock`}
                        type="number"
                        min={1}
                        max={20}
                      />
                      <FormField
                        name={`items[${index}].price`}
                        label="Price"
                        type="number"
                        step="0.01"
                        min={1}
                      />
                      <FormField
                        name={`items[${index}].color`}
                        label="Color"
                        value={values.items[index].color.label}
                        type="text"
                        disabled
                      />
                      <FormField
                        name={`items[${index}].images.name`}
                        label="Images"
                        type="file"
                        multiple
                        onChange={(event) => {
                          Object.values(event.currentTarget.files).map((file) =>
                            values.items[index].images.push(file)
                          )
                        }}
                      />
                    </div>
                  ))}
                </div>
              )}
            />
            <div className="flex justify-center mt-5">
              <Button
                className="bg-blue-600 hover:bg-blue-700 active:bg-blue-500 rounded mt-2"
                type="submit"
                disabled={!isValid || isSubmitting}
              >
                Add furniture
              </Button>
            </div>
          </Form>
        )}
      </Formik>
    </section>
  )
}
export default FormAddFurniture
