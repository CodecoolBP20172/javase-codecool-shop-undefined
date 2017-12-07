
function addRadioEventListeners() {
    $('#credit_card_input').hide();
    $('#paypal_input').hide();

    $('input[type="radio"]').on('change', function () {
        if (this.value === 'Credit Card') {
            $('#credit_card_input').show();
            $('#paypal_input').hide();
        } else if (this.value === 'Paypal') {
            $('#credit_card_input').hide();
            $('#paypal_input').show();
        }
    });
}

window.addEventListener('load', addRadioEventListeners);