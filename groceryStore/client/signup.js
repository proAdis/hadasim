// שמירת נתונים ב-localStorage וניווט לדף הכניסה לאחר ההרשמה
document.getElementById("signupBtn").addEventListener("click", function() {
  let company = document.getElementById("CompanyName").value.trim();
  let phone = document.getElementById("phone").value.trim();
  let agent = document.getElementById("agent").value.trim();
  let supplierGoods = document.getElementById("supplierGoods").value.trim();
  let password = document.getElementById("signupPassword").value.trim();

  if (!company || !phone || !agent || !supplierGoods || !password) {
    swal({ text: "נא למלא את כל השדות" });
    return;
  }

  let users = JSON.parse(localStorage.getItem("users")) || [];

  // בדיקת אם החברה כבר קיימת
  if (users.some(user => user.company === company)) {
    swal({ text: "החברה כבר קיימת במערכת" });
    return;
  }

  // הוספת המשתמש ל-localStorage
  let user = {
    company: company,
    phone: phone,
    agent: agent,
    supplierGoods: supplierGoods,
    password: password
  };

  users.push(user);
  localStorage.setItem("users", JSON.stringify(users));

  // מעבר לדף "welcome.html" לאחר הרשמה
  swal({ text: "ההרשמה בוצעה בהצלחה!" }).then(() => {
    window.location.href = "welcome.html";  // העברה לדף הוולקום
  });
});
