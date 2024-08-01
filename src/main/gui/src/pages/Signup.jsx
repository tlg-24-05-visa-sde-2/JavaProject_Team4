/*
* Welcome to the signup page! Lots of websites use some form of a signup page, here you have the basic neccessities of a signup page
* You have full control over customization of this page
* 
* The form is ready to be validated and ensure matching passwords, it is encouraged that you add a regex to the passwords to enforce modern password strength requirements
*
*/

/* eslint-disable no-lone-blocks */
/* eslint-disable jsx-a11y/anchor-is-valid */
import React, { useState } from "react";
import Button from "react-bootstrap/Button";
import Col from "react-bootstrap/Col";
import Form from "react-bootstrap/Form";
import InputGroup from "react-bootstrap/InputGroup";
import Row from "react-bootstrap/Row";
import "../assets/css/signup.css";
import AuthService from "../utils/AuthService";
import { faWindowMinimize } from "@fortawesome/free-regular-svg-icons";
import { toast } from "react-toastify";

function Signup() {
  // State variable to hold our form data
  const [formState, setFormState] = useState({
    username: "",
    password1: "",
    password2: "",
  });

  const [validated, setValidated] = useState(false);
  const [passwordsMatch, setPasswordsMatch] = useState(true);

  // Event handler to update our form state
  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormState((prevState) => ({ ...prevState, [name]: value }));

    // Reset password match state on input change
    if (name === "password1" || name === "password2") {
      setPasswordsMatch(true);
    }
  };

  // Choose what to do when the form is submitted!
  const handleSubmit = async (event) => {
    event.preventDefault();
    event.stopPropagation();

    const data = {
      username: formState.username,
      password: formState.password1,
    }

    const form = event.currentTarget;
    const doPasswordsMatch = formState.password1 === formState.password2;

    if (!form.checkValidity() || !doPasswordsMatch) {
      if (!doPasswordsMatch) {
        setPasswordsMatch(false);
      }
      setValidated(false);
    } else {
      setValidated(true);

      // Submit form or perform desired action here
      // Check out the AuthService class to update and/or modify to your desired logic
      const response = await AuthService.handleSignup(data);

      if(response.status === 200) {
        // Redirect to login page or any other page
        window.location.href = "/login";
      } else {  
        toast.error(response.message);
      }

      console.log(response);
    }
  };

  return (
    <div className="signup-wrapper">
      <div className="signup-container">
        <h2>Sign Up</h2>
        <Form noValidate validated={validated} onSubmit={handleSubmit} className="text-light">
          <Row className="mb-4">
            <Form.Group as={Col} md="12">
              <Form.Label>Username</Form.Label>
              <InputGroup hasValidation>
                <Form.Control
                  type="text"
                  name="username"
                  id="username"
                  placeholder="John123"
                  aria-describedby="inputGroupPrepend"
                  autoComplete="username"
                  onChange={handleInputChange}
                  required
                />
                <Form.Control.Feedback type="invalid">
                  Please provide a valid username.
                </Form.Control.Feedback>
              </InputGroup>
            </Form.Group>
          </Row>
          <Row className="mb-3">
            <Form.Group as={Col} md="12 mb-4">
              <Form.Label>Password</Form.Label>
              <InputGroup hasValidation>
                <Form.Control
                  type="password"
                  name="password1"
                  id="password1"
                  placeholder="******"
                  aria-describedby="inputGroupPrepend"
                  autoComplete="new-password"
                  onChange={handleInputChange}
                  required
                />
                <Form.Control.Feedback type="invalid">
                  Please provide a valid password.
                </Form.Control.Feedback>
              </InputGroup>
            </Form.Group>
            <Form.Group as={Col} md="12 mb-2">
              <Form.Label>Verify Password</Form.Label>
              <InputGroup hasValidation>
                <Form.Control
                  type="password"
                  name="password2"
                  id="password2"
                  placeholder="******"
                  aria-describedby="inputGroupPrepend"
                  autoComplete="new-password"
                  onChange={handleInputChange}
                  required
                />
                <Form.Control.Feedback type="invalid">
                  Passwords must match.
                </Form.Control.Feedback>
              </InputGroup>
              {!passwordsMatch && (
                <div className="invalid-feedback d-block mt-2">
                  Passwords do not match!
                </div>
              )}
            </Form.Group>
          </Row>
          <Form.Group className="mb-3">
            <Form.Check
              required
              label="Agree to terms and conditions"
              feedback="You must agree before submitting."
              feedbackType="invalid"
              className="text-dark"
            />
            <a href="#" target="_blank" rel="noreferrer">
              View Terms and conditions
            </a>
          </Form.Group>
          <Button type="submit">Submit</Button>
        </Form>
      </div>
    </div>
  );
}

export default Signup;