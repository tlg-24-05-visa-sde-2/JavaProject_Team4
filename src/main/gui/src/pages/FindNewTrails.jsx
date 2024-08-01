import React from "react";
import {
  MDBCard,
  MDBCardBody,
  MDBCardTitle,
  MDBCardText,
  MDBCardImage,
  MDBBtn,
} from "mdb-react-ui-kit";
import { toast } from "react-toastify";
import UserService from "../utils/UserService";
import * as images from "../assets/images/images.js";
import TrailService from "../utils/TrailServcie";
import Header from "../components/Header";
import "../assets/css/newTrails.css";


function FindNewTrails(props) {
    const [trails, setTrails] = React.useState([]);
    const [user, setUser] = React.useState(null);
    const [isLoading, setIsLoading] = React.useState(true);
    const isAuthenticated = props.isAuthenticated;
  
    const fetchTrails = async () => {
      const res = await TrailService.getTrails();
      if (res && typeof res === "object") {
        const trailsArray = Object.values(res).map((trail) => ({
          ...trail,
          imageUrl: images[`image${Math.floor(Math.random() * 15) + 1}`],
        }));
        const userData = await UserService.getUser();
        setUser(userData);
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
      console.log(user);
    }, [trails, user]);
  
    const isTrailFavorited = (trail) => {
      return user && user.trails.some(favorite => favorite.name === trail.name);
    };
  
    async function handleAddToFavorites(trail) {
      const dataToSave = {
        appId: trail.place_id,
        name: trail.name,
        trailLength: trail.activities.hiking.length,
      };
  
      const response = await TrailService.favoriteTrail(dataToSave);
      if(response) {
        toast.success("Trail added to favorites!");
        const updatedUser = await UserService.getUser();
        setUser(updatedUser);
      } else {
        toast.error("Failed to add trail to favorites.");
      }
    }
  
    async function handleRemoveFromFavorites(trail) {
      const response = await TrailService.unfavoriteTrail(trail.place_id);
      if(response) {
        toast.success("Trail removed from favorites!");
        const updatedUser = await UserService.getUser();
        setUser(updatedUser);
      } else {
        toast.error("Failed to remove trail from favorites.");
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
                      {isTrailFavorited(trail) ? (
                        <MDBBtn
                          href="#"
                          className="btn-danger"
                          onClick={() => handleRemoveFromFavorites(trail)}
                        >
                          Remove
                        </MDBBtn>
                      ) : (
                        <MDBBtn
                          href="#"
                          onClick={() => handleAddToFavorites(trail)}
                        >
                          Add To favorites
                        </MDBBtn>
                      )}
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