import HttpService from "./HttpService";

let exemploService = {
  listar: () => {
    return HttpService.backApi.get("/exemplos");
  },
  consultarPorId: (id) => {
    return HttpService.backApi.get(`/exemplos/${id}`)
  },
  cadastrar: exemplo => {
    return HttpService.backApi.post("/exemplos", exemplo);
  },
  atualizar: (id, exemplo) => {
    return HttpService.backApi.put(`/exemplos/${id}`, exemplo);
  },
  remover: id => {
    return HttpService.backApi.delete(`/exemplos/${id}`)
  }
};

export default exemploService;
