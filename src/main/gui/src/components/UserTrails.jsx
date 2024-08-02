import React from "react";
import {
  MDBCard,
  MDBCardBody,
  MDBCardTitle,
  MDBCardText,
  MDBBtn,
} from "mdb-react-ui-kit";
import { toast } from "react-toastify";
import TrailService from "../utils/TrailServcie";
import "../assets/css/newTrails.css";

function UserTrails(props) {
  async function handleRemoveFromFavorites(trailId) {
    const response = await TrailService.unfavoriteTrail(trailId);
    if (response) {
      window.location.reload();
      toast.success("Trail removed from favorites!");
    } else {
      toast.error("Failed to remove trail from favorites.");
    }
  }
  const user = props.user;
  return (
    <div className="card-container">
      {user?.trails?.map((trail, index) => (
        <MDBCard className="trail-card" key={index}>
          <MDBCardBody>
            <MDBCardTitle>{trail.name}</MDBCardTitle>
            <MDBCardText>{trail.trailLength} miles</MDBCardText>
            <MDBCardText>difficulty: {trail.experience}</MDBCardText>
            <MDBBtn
              className="btn-danger"
              onClick={() => handleRemoveFromFavorites(trail.trailId)}
            >
              Remove
            </MDBBtn>
          </MDBCardBody>
        </MDBCard>
      ))}
    </div>
  );
}

export default UserTrails;
