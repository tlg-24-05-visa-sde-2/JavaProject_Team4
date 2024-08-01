import { toast } from "react-toastify";
class AuthService {
  static async handleLogin(data) {
    // Update the URL to go to your server OR handle you login's logic here
    const url = `http://localhost:8080/auth/login`;
    const response = await fetch(url, {
      method: "POST",
      credentials: "include", // Remove this if don't need it
      headers: {
        "Content-type": "application/json",
      },
      body: JSON.stringify(data),
    });

    const res = await response.json();
  
    return res;
  }

  static async handleSignup(data) {
    const url = `http://localhost:8080/auth/signup`;

    const bodyJsonData = JSON.stringify(data);
    console.log(bodyJsonData);

    const response = await fetch(url, {
      method: "POST",
      credentials: "include", // Remove this if don't need it
      headers: {
        "Content-type": "application/json",
      },
      body: bodyJsonData,
    });

    const res = await response.text();
    if(response.status === 200) {
      return true;
    } else {
      toast.error(res);
      return false;
    }
  }

  static async checkLogin() {
    try {
      let url = `http://localhost:8080/auth/isLoggedIn`;

      const response = await fetch(url, {
        credentials: "include",
        method: "GET",
        headers: {
          "Content-type": "application/json",
        },
      });

      return response.status === 200; // We just check whether the status is 200 or not using true or false
    } catch (error) {
      console.error("error occured while making request", error);
      return false;
    }
  }

  static async logout() {
    try {
      let url = `http://localhost:8080/auth/logout`;

      const response = await fetch(url, {
        credentials: "include",
        method: "GET",
        headers: {
          "Content-type": "application/json",
        },
      });

      window.location.replace("/login");
      return response.status === 200; // We just check whether the status is 200 or not using true or false
    } catch (error) {
      console.error("error occured while making request", error);
      return false;
    }
  }
}

export default AuthService;