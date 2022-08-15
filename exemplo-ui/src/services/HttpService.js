import Axios from "axios";

let headers = {
  Accept: "application/json"
};

const backApi = Axios.create({
  baseURL: process.env.VUE_APP_BACK_API,
  timeout: 99999,
  headers: headers
});

backApi.interceptors.response.use(
  response => {
    return response;
  },
  error => {
    
    if(error.response.status == 500) {
      let errorResponse = {
        message: 'Erro desconhecido do servidor',
        status: error.response.status
      }
      return Promise.reject(errorResponse);
    } else {
      let errorResponse = {
        message: error.response.data.message,
        status: error.response.status
      }
      return Promise.reject(errorResponse);
    }
  }
);

export default {
  backApi: backApi
};
