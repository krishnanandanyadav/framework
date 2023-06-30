class RegistrationPage {
    elements = {
        patientName: () => cy.get("#patient-name"),
        patientPhoneNo: () => cy.get("#Phone-No"),
        patientAge: () => cy.get("#patient-age"),
        patientEmail: () => cy.get("#exampleInputEmail1"),
        patientPassword: () => cy.get("#exampleInputPassword1"),
        patientCountry: () => cy.get("#country-id"),
        submitBtn: () => cy.get('button[type="submit"]'),
        toastMessage: () => cy.get('div[role="alert"] > div:nth-child(2)')
    };

    enterPatientName(username) {
        this.elements.patientName().type(username);
    }

    enterPatientPhoneNo(phoneNo) {
        this.elements.patientPhoneNo().type(phoneNo);
    }

    enterPatientAge(age) {
        this.elements.patientAge().type(age);
    }

    enterPatientEmail(email) {
        this.elements.patientEmail().type(email);
    }

    enterPatientPassword(password) {
        this.elements.patientPassword().type(password);
    }

    selectPatientCountry(countryName){
        this.elements.patientCountry().select(countryName);
    }

    clickSubmitButton(){
        this.elements.submitBtn().click();
    }
}

export const registrationPage = new RegistrationPage();