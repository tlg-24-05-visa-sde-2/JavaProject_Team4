import React from "react";
import UserService from "../utils/UserService";
import AuthService from "../utils/AuthService";
import UserTrails from "./UserTrails";
import "../assets/css/home.css";

function HomeProfile() {
  const [user, setUser] = React.useState({});
  const [isLoading, setIsLoading] = React.useState(true);

  const fetchUser = async () => {
    const response = await UserService.getUser();
    console.log(response);
    setUser(response);
    setIsLoading(false);
  };

  React.useEffect(() => {
    fetchUser();
  }, []); // Empty dependency array means this runs once when the component mounts

  if (isLoading) {
    return <h1>Loading...</h1>;
  }

  return (
    <div className="user-profile">
      <h1>Welcome {user?.username}!</h1>
        <button
          className="align-self-end new-trails-button"
          onClick={() => window.location.replace("/find-trails")}
        >
          Find New Trails
        </button>
      <h2 className="align-self-start mt-5">Your Favorited Trails:</h2>
      <UserTrails user={user} />
    </div>
  );
}

export default HomeProfile;
