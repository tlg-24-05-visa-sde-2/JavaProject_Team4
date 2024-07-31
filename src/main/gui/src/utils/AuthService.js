class AuthService {
  static async handleLogin(data) {
    // Update the URL to go to your server OR handle you login's logic here
    const url = `http://localhost:8080/user/login`;
    const response = await fetch(url, {
      method: "POST",
      credentials: "include", // Remove this if don't need it
      headers: {
        "Content-type": "application/json",
      },
      body: JSON.stringify(data),
    });

    // DO WHAT YOU WANT WITH THE RESPONSE HERE
    return response;
  }

  static async handleSignup(data) {
    const url = `http://localhost:8080/user/signup`;

    const response = await fetch(url, {
      method: "POST",
      credentials: "include", // Remove this if don't need it
      headers: {
        "Content-type": "application/json",
      },
      body: JSON.stringify(data),
    });

    return response;
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
}

export default AuthService;
