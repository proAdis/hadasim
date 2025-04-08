let loginCompanyInput = document.querySelector("input[placeholder='שם חברה']");
let loginPasswordInput = document.querySelector("input[placeholder='סיסמה']");
let loginBtn = document.querySelector(".botton");

let users = [];

// טען משתמשים מה-localStorage
if (localStorage.getItem("users") != null) {
  users = JSON.parse(localStorage.getItem("users"));
}

//  התחברות
function login() {
  let company = loginCompanyInput.value.trim();
  let password = loginPasswordInput.value;

 
  if (!company || !password) {
    Swal.fire({  
      text: "נא למלא שם חברה וסיסמה"
    });
    return;
  }

  // חיפוש משתמש תואם
  let user = users.find(u => u.company === company && u.password === password);

  if (user) {
    Swal.fire({  
      text: "ברוך הבא, " + user.agent
    });

    // שמור את שם החברה ב-localStorage למעבר לדף הבא
    localStorage.setItem("currentUserCompany", user.company);

    // במקום לדף המתואר במשימה מעבר לדף ברוך הבא
    window.location.href = "welcome.html";
  } else {
    Swal.fire({ 
      text: "שם חברה או סיסמה שגויים"
    });
  }
}

// לחיצה על כפתור "כניסה"
loginBtn.addEventListener("click", login);
