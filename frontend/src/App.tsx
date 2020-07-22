import React, { useState, FC, useEffect } from "react";
import "./App.css";
import NavigationBar from "./components/NavigationBar";
import api from "./api/api";
import { ApiResponse } from "apisauce";

interface HelloWorld {
  id: number;
  content: string;
}

const App = () => {
  const [helloWorld, setHelloWorld] = useState<HelloWorld | unknown>({
    id: 0,
    content: "",
  });

  useEffect(() => {
    api
      .get("/greeting")
      .then((res) => res.data)
      .then((res: HelloWorld | unknown) => setHelloWorld(res));
  }, []);

  return (
    <React.Fragment>
      <NavigationBar title="Это навигацияяяя" />
      <h1>
        id: {(helloWorld as HelloWorld).id} content:{" "}
        {(helloWorld as HelloWorld).content}
      </h1>
    </React.Fragment>
  );
};

export default App;
