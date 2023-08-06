import React, { Suspense } from "react";
import ReactDOM from "react-dom/client";
import "./index.scss";
import Layout from "./components/layout/Layout.tsx";
import { BrowserRouter } from "react-router-dom";
import Loader from "./components/loader/Loader.tsx";
import App from "./App";
import { AuthProvider } from "./context/AuthContext";
import { Provider } from "react-redux";
import store from "./store/store";

const root = ReactDOM.createRoot(document.getElementById("root"));

root.render(
  <BrowserRouter>
    <AuthProvider>
      <Provider store={store}>
        <Layout>
          <Suspense fallback={<Loader />}>
            <App />
          </Suspense>
        </Layout>
      </Provider>
    </AuthProvider>
  </BrowserRouter>
);
