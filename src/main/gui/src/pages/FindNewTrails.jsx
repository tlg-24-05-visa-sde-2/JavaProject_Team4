import React from "react";
import {
  MDBCard,
  MDBCardBody,
  MDBCardTitle,
  MDBCardText,
  MDBCardImage,
  MDBBtn,
} from "mdb-react-ui-kit";
import * as images from "../assets/images/images.js";

import TrailService from "../utils/TrailServcie";
import Header from "../components/Header";
import "../assets/css/newTrails.css";

function FindNewTrails(props) {
  const [trails, setTrails] = React.useState([]);
  const [isLoading, setIsLoading] = React.useState(true);
  const isAuthenticated = props.isAuthenticated;

  const fetchTrails = async () => {
    const res = await TrailService.getTrails();
    if (res && typeof res === "object") {
      const trailsArray = Object.values(res).map(trail => ({
        ...trail,
        imageUrl: images[`image${Math.floor(Math.random() * 15) + 1}`]
      }));
      setTrails(trailsArray);
      setIsLoading(false);
    } else {
      console.error("Expected an object but got:", res);
    }
  };

  React.useEffect(() => {
    fetchTrails();
  }, []);

  React.useEffect(() => {
    console.log(trails);
  }, [trails]);

  function handleAddToFavorites(trail) {
    console.log("Adding trail to favorites:");
    
    const dataToSave = {
        "appId": trail.place_id,
        "name": trail.name,
        "trailLength": trail.activities.hiking.length,
    }
}

  return (
    <div>
      {isAuthenticated && <Header isAuthenticated={isAuthenticated} />}
      <div className="new-trails-container">
        <h1>Find New Trails</h1>

        {isLoading ? (
          <h1>Loading...</h1>
        ) : (
          <div className="card-container">
            {trails.length > 0 ? (
              trails.map((trail, index) => (
                <MDBCard key={index} className="trail-card">
                  <MDBCardImage
                    src={trail.imageUrl}
                    position="top"
                    alt={trail.name}
                  />
                  <MDBCardBody className="card-body">
                    <MDBCardTitle>{trail.name}</MDBCardTitle>
                    <MDBCardText>
                      {trail.city}, {trail.state}, {trail.country}
                    </MDBCardText>
                    <MDBCardText>{trail.description}</MDBCardText>
                    <MDBBtn href="#" onClick={() => handleAddToFavorites(trail)}>Add To favorites</MDBBtn>
                  </MDBCardBody>
                </MDBCard>
              ))
            ) : (
              <p>No trails found.</p>
            )}
          </div>
        )}
      </div>
    </div>
  );
}

export default FindNewTrails;
