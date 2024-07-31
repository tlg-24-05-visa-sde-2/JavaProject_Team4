import React from "react";
import {
    MDBCard,
    MDBCardBody,
    MDBCardTitle,
    MDBCardHeader,
    MDBBtn,
    MDBListGroup,
  } from "mdb-react-ui-kit";
  
function LoginSignup() {
  return (
    <MDBCard alignment="center" className="w-50 align-self-center">
      <MDBCardHeader>Your favorite trail finder!</MDBCardHeader>
      <MDBCardBody>
        <MDBCardTitle>Create an account or sign in</MDBCardTitle>
        <MDBListGroup className="w-25 mx-auto d-flex flex-column align-items-center">
          <MDBBtn href="login" className="m-1">
            Login
          </MDBBtn>
          <MDBBtn href="signup" className="m-1">
            SignUp
          </MDBBtn>
        </MDBListGroup>
      </MDBCardBody>
    </MDBCard>
  );
}

export default LoginSignup;
