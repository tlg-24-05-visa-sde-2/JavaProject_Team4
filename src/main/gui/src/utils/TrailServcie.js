class TrailService {
    static async getTrails() {
        const response = await fetch("http://localhost:8080/trail/getAllHikingTrails", {
            method: "GET",
            credentials: "include",
            headers: {
                "Content-type": "application/json",
            },
        });

        if (response.status === 200) {
            const trails = await response.json();
            return trails;
        } else {
            return { error: "An error occurred while fetching trail data" };
        }
    }
}

export default TrailService;