class LoginPage {
  elements = {
    usernameInput: () => cy.get("#exampleInputEmail1"),
    passwordInput: () => cy.get("#exampleInputPassword1"),
    loginBtn: () => cy.get('button[type = "submit"]'),
    toastMessage: () => cy.get('div[role="alert"] > div:nth-child(2)'),
  };

  typeUsername(username) {
    this.elements.usernameInput().type(username);
  }

  typePassword(password) {
    this.elements.passwordInput().type(password);
  }

  clickLogin() {
    this.elements.loginBtn().click();
  }

  submitLogin(username,password){
    this.elements.usernameInput().type(username);
    this.elements.passwordInput().type(password);
    this.elements.loginBtn().click();
  }
}

export const loginPage = new LoginPage();
