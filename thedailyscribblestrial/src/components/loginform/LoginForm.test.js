import React from "react";
import { render, fireEvent, waitFor, screen } from "@testing-library/react";
import LoginForm from "./LoginForm";

describe("LoginForm component", () => {
  test("renders login form with username and password inputs", () => {
    render(<LoginForm />);
    expect(screen.getByLabelText("Username")).toBeInTheDocument();
    expect(screen.getByLabelText("Password")).toBeInTheDocument();
  });

  test("calls login function when form is submitted", async () => {
    const loginMock = jest.fn();
    render(<LoginForm login={loginMock} />);
    fireEvent.change(screen.getByLabelText("Username"), {
      target: { value: "testuser" },
    });
    fireEvent.change(screen.getByLabelText("Password"), {
      target: { value: "testpassword" },
    });
    fireEvent.submit(screen.getByRole("button", { name: "Continue" }));
    await waitFor(() => expect(loginMock).toHaveBeenCalledTimes(1));
  });

  test("shows error message when login fails", async () => {
    const error = "Incorrect username or password";
    const loginMock = jest
      .fn()
      .mockRejectedValue({ response: { data: error } });
    render(<LoginForm login={loginMock} />);
    fireEvent.change(screen.getByLabelText("Username"), {
      target: { value: "testuser" },
    });
    fireEvent.change(screen.getByLabelText("Password"), {
      target: { value: "testpassword" },
    });
    fireEvent.submit(screen.getByRole("button", { name: "Continue" }));
    await waitFor(() => expect(screen.getByText(error)).toBeInTheDocument());
  });

  test("redirects to home page when user is authenticated", () => {
    const navigateMock = jest.fn();
    const authContext = {
      isAuth: true,
      isAdmin: false,
      isModerator: false,
    };
    render(
      <AuthContext.Provider value={authContext}>
        <LoginForm role="blogger" navigate={navigateMock} />
      </AuthContext.Provider>
    );
    expect(navigateMock).toHaveBeenCalledWith("/");
  });

  test("redirects to admin home page when user is authenticated as admin", () => {
    const navigateMock = jest.fn();
    const authContext = {
      isAuth: true,
      isAdmin: true,
      isModerator: false,
    };
    render(
      <AuthContext.Provider value={authContext}>
        <LoginForm role="admin" navigate={navigateMock} />
      </AuthContext.Provider>
    );
    expect(navigateMock).toHaveBeenCalledWith("../admin/home");
  });

  test("redirects to moderator home page when user is authenticated as moderator", () => {
    const navigateMock = jest.fn();
    const authContext = {
      isAuth: true,
      isAdmin: false,
      isModerator: true,
    };
    render(
      <AuthContext.Provider value={authContext}>
        <LoginForm role="moderator" navigate={navigateMock} />
      </AuthContext.Provider>
    );
    expect(navigateMock).toHaveBeenCalledWith("../mod/home");
  });
});
