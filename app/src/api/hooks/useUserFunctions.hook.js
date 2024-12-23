import { axiosInstance } from '../user/_base/axiosInstance';

export function useUserFunctions() {
  async function getUserInfo(userId) {
    try {
      const response = await axiosInstance.get('/usuarios/info/' + userId);
      return response.data;
    } catch (error) {
      return error;
    }
  }

  async function getUserByName(name, size) {
    try {
      const response = await axiosInstance.get(
        '/usuarios/pesquisa?sort=nome,asc&size=' + size + '&text=' + name
      );
      return response.data;
    } catch (error) {
      return error;
    }
  }
  async function sendUserInitialInformation(userInfo) {
    try {
      const response = await axiosInstance.post('/usuarios/infoIniciais/', {
        sexo: userInfo.sex.value,
        peso: userInfo.weight.value,
        altura: userInfo.height.value,
        meta: userInfo.goal.value,
        atividadeFisica: userInfo.activity.value,
        imagem: userInfo.image.value
      });
      return response.data;
    } catch (error) {
      return error;
    }
  }
  async function getUserDetails() {
    try {
      const response = await axiosInstance.get('/usuarios/me/info');
      return response.data;
    } catch (error) {
      return error;
    }
  }

  async function getUserConversations() {
    try {
      const response = await axiosInstance.get('/usuarios/me/conversas');
      return response.data;
    } catch (error) {
      return error;
    }
  }

  async function getUserMessages(conversationId) {
    try {
      const response = await axiosInstance.get(
        '/usuarios/me/mensagens/' + conversationId
      );
      return response.data;
    } catch (error) {
      return error;
    }
  }

  async function getUserRecipes(userId) {
    try {
      const response = await axiosInstance.get('/receitas/usuario/' + userId);
      return response.data;
    } catch (error) {
      return error;
    }
  }
  async function getUserAchievements(userId) {
    try {
      const response = await axiosInstance.get('/conquistas/usuario/' + userId);
      return response.data;
    } catch (error) {
      return error;
    }
  }
  async function followUser(userId) {
    try {
      await axiosInstance.post('/usuarios/seguir/' + userId);
    } catch (error) {
      return error;
    }
  }
  async function unfollowUser(userId) {
    try {
      await axiosInstance.post('/usuarios/unfollow/' + userId);
    } catch (error) {
      return error;
    }
  }
  async function addConversation(userId) {
    try {
      await axiosInstance.put('/usuarios/me/conversas/adicionar/' + userId);
    } catch (error) {
      return error;
    }
  }

  return {
    getUserDetails,
    getUserInfo,
    getUserAchievements,
    getUserRecipes,
    followUser,
    unfollowUser,
    sendUserInitialInformation,
    getUserByName,
    getUserConversations,
    getUserMessages,
    addConversation,
  };
}
