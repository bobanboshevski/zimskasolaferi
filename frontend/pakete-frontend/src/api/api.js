import axios from 'axios';


const api = axios.create({
    baseURL: process.env.REACT_APP_BACKEND_URL,
    timeout: 5000,
    headers: {
        "Content-Type": "application/json",
        Accept: "application/json",
    },
});
const makeRequest = async (options) => {
    try {
        const response = await api(options);
        return response.data;
    } catch (error) {
        // Check if the error is due to the server being unreachable
        if (!error.response) {
            // Return a custom error object or message
            return { error: true, message: "Server not available" };
        }
        // Handle other types of errors (e.g., HTTP status code errors)
        console.error("API Error:", error.response ? error.response.data : error.message);
        return error.response ? error.response.data : { error: error.message };
    }
};
const ApiService = {
    get: (url, options = {}) => {
        //console.log(options.params);
        return makeRequest({ method: "GET", url, params: options.params  }); //headers: options.headers
    },

    post: (url, options = {}) => {
        return makeRequest({ method: "POST", url, data: options.data, headers: options.headers });
    },

    put: (url, options = {}) => {
        return makeRequest({ method: "PUT", url, data: options.data, headers: options.headers });
    },

    delete: (url, options = {}) => {
        return makeRequest({ method: "DELETE", url, data: options.data, headers: options.headers });
    },
};

export default ApiService;