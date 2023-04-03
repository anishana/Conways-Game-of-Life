import axios from 'axios';


const api = axios.create({
    baseURL: "http://localhost:8080"
})

export const initializeBoard = (payload) => api.post(`/api/initializeBoard`, payload);
export const updateBoard = (payload) => api.post(`/api/updateBoard`, payload);
