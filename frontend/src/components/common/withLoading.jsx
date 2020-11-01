import React from "react";
import Loading from "./loading";

const WithLoading = (Component) => {
  return function WithLoadingComponent({ isLoading, ...props }) {
    if (!isLoading) return <Component {...props} />;
    return <Loading />;
  };
};

export default WithLoading;
