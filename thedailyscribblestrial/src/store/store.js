import { createContext } from "react";
import { createSlice, configureStore } from "@reduxjs/toolkit";
import loginService from "../services/login-service";
import registerService from "../services/register-service";

export const AuthContext = createContext();

const authSlice = createSlice({
  name: "auth",
  initialState: {
    user: null,
    isAuth: false,
    isAdmin: false,
    isModerator: false,
    postsType: "latest",
  },
  reducers: {
    setUser: (state, action) => {
      state.user = action.payload;
    },
    setIsAuth: (state, action) => {
      state.isAuth = action.payload;
    },
    setIsAdmin: (state, action) => {
      state.isAdmin = action.payload;
    },
    setIsModerator: (state, action) => {
      state.isModerator = action.payload;
    },
    setPostsType: (state, action) => {
      state.postsType = action.payload;
    },
    logout: (state) => {
      state.user = null;
      state.isAuth = false;
      state.isAdmin = false;
      state.isModerator = false;
    },
  },
});

export const {
  setUser,
  setIsAuth,
  setIsAdmin,
  setIsModerator,
  setPostsType,
  logout,
} = authSlice.actions;

export const login = (role, userName, password) => async (dispatch) => {
  const response = await loginService(role, userName, password);
  if (response.data) {
    dispatch(setUser(response.data));
    dispatch(setIsAuth(true));
    if (response.data.role === "admin") {
      dispatch(setIsAdmin(true));
    }
    if (response.data.role === "mod") {
      dispatch(setIsModerator(true));
    }
    localStorage.setItem("user", JSON.stringify(response.data));
  }
  return response;
};

export const register = (userName, password) => async (dispatch) => {
  const response = await registerService(userName, password);
  if (response.data) {
    dispatch(setUser(response.data));
    dispatch(setIsAuth(true));
    localStorage.setItem("user", JSON.stringify(response.data));
  }
  return response;
};

const store = configureStore({
  reducer: {
    auth: authSlice.reducer,
  },
});

export const AuthProviderStore = ({ children }) => {
  return <AuthContext.Provider value={store}>{children}</AuthContext.Provider>;
};

export default store;
