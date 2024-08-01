class UserService {
  static async getUser() {
    const request = await fetch("http://localhost:8080/user/getUser", {
      method: "GET",
      credentials: "include",
      headers: {
        "Content-type": "application/json",
      },
    });

    if (request.status === 200) {
      const response = await request.json();
      return response;
    } else {
      return { error: "An error occurred while fetching user data" };
    }
  }
}

export default UserService;
