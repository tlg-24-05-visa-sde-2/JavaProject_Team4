import React from "react";
import AuthService from "../utils/AuthService";

function Header(props) {
  const isAuthenticated = props.isAuthenticated;
  return (
    <div className="header-div">
      <h1 className="text-center">Go take a hike!</h1>
      <div>
        <button onClick={() => window.location.replace("/")}>Home</button>
        {isAuthenticated && (
            <button className="logout" onClick={() => AuthService.logout()}>
                Logout
            </button>
        )}
      </div>
    </div>
  );
}

export default Header;