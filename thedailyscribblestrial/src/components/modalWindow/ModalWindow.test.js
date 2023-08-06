import React from "react";
import { render, fireEvent } from "@testing-library/react";
import ModalWindow from "./ModalWindow";

describe("ModalWindow", () => {
  it("should not be visible when showModal is false", () => {
    const { getByRole } = render(
      <ModalWindow showModal={false} setShowModal={() => {}} />
    );
    const modalElement = getByRole("dialog");
    expect(modalElement).not.toBeVisible();
  });

  it("should be visible when showModal is true", () => {
    const { getByRole } = render(
      <ModalWindow showModal={true} setShowModal={() => {}} />
    );
    const modalElement = getByRole("dialog");
    expect(modalElement).toBeVisible();
  });

  it("should call setShowModal with false when close button is clicked", () => {
    const setShowModalMock = jest.fn();
    const { getByRole } = render(
      <ModalWindow showModal={true} setShowModal={setShowModalMock} />
    );
    const closeButton = getByRole("button", { name: "Close" });
    fireEvent.click(closeButton);
    expect(setShowModalMock).toHaveBeenCalledWith(false);
  });

  it('should navigate to the login page when "Log in" button is clicked', () => {
    const navigateMock = jest.fn();
    const { getByRole } = render(
      <ModalWindow showModal={true} setShowModal={() => {}} />
    );
    const loginButton = getByRole("button", { name: "Log in" });
    fireEvent.click(loginButton);
    expect(navigateMock).toHaveBeenCalledWith("/login");
  });

  it('should navigate to the registration page when "Create account" button is clicked', () => {
    const navigateMock = jest.fn();
    const { getByRole } = render(
      <ModalWindow showModal={true} setShowModal={() => {}} />
    );
    const createAccountButton = getByRole("button", { name: "Create account" });
    fireEvent.click(createAccountButton);
    expect(navigateMock).toHaveBeenCalledWith("/register");
  });
});
