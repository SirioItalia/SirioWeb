import { useCallback, useContext, useState, useEffect } from "react"
import Button from "@components/Form/Button.jsx"
import FormField from "@components/Form/FormField.jsx"
import { makeClient } from "@services/makeClient.js"
import { AppContext } from "@components/Context/AppContext"
import Link from "next/link"
import Router from "next/router"
import * as yup from "yup"
import { Formik, Form, FieldArray } from "formik"

const initialValues = {
  email: "",
  password: "",
}

const validationSchema = yup.object().shape({
  email: yup.string().email().required().label("email"),
  password: yup.string().min(8).required().label("password"),
})

const FormHandler = (props) => {
  const { sectionTitle, children } = props
  const handleFormSubmit = useCallback(async ({ email, password }) => {
    setError(null)
  }, [])

  return (
    <section>
      <h2 className="font-sans font-bold break-normal text-gray-700 px-2 pb-8 text-xl">
        {sectionTitle}
      </h2>
      <Formik
        onSubmit={handleFormSubmit}
        initialValues={initialValues}
        validationSchema={validationSchema}
      >
        {({ handleSubmit, isValid, isSubmitting }) => (
          <form
            onSubmit={handleSubmit}
            noValidate
            className="shadow-md rounded p-10 mb-4 bg-white w-full"
          >
            {children}
          </form>
        )}
      </Formik>
    </section>
  )
}
export default FormHandler
