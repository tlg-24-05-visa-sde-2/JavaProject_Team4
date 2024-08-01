import React from "react";
import {
  MDBCard,
  MDBCardBody,
  MDBCardTitle,
  MDBCardText,
  MDBBtn,
} from "mdb-react-ui-kit";
import "../assets/css/newTrails.css";

function UserTrails(props) {
  const user = props.user;
  return (
    <div className="card-container">
      {user?.trails?.map((trail, index) => (
        <MDBCard className="trail-card" key={index}>
          <MDBCardBody>
            <MDBCardTitle>{trail.name}</MDBCardTitle>
            <MDBCardText>{trail.trailLength} miles</MDBCardText>
            <MDBCardText>difficulty: {trail.experience}</MDBCardText>
            <MDBBtn className="btn-danger">Remove</MDBBtn>
          </MDBCardBody>
        </MDBCard>
      ))}
    </div>
  );
}

export default UserTrails;
