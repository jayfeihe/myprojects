// 构造函数，变量为15位或者18位的身份证号码
function clsIDCard(CardNo) {
	this.Valid = false;
	this.ID15 = "";
	this.ID18 = "";
	this.Local = "";
	if (CardNo != null) {
		this.SetCardNo(CardNo);
	}
}

// 设置身份证号码，15位或者18位
clsIDCard.prototype.SetCardNo = function (CardNo) {
	this.ID15 = "";
	this.ID18 = "";
	this.Local = "";
	CardNo = CardNo.replace(" ", "");
	var strCardNo;
	if (CardNo.length == 18) {
		pattern = /^\d{17}(\d|x|X)$/;
		if (pattern.exec(CardNo) == null) {
			return;
		}
		strCardNo = CardNo.toUpperCase();
	} else {
		pattern = /^\d{15}$/;
		if (pattern.exec(CardNo) == null) {
			return;
		}
		strCardNo = CardNo.substr(0, 6) + "19" + CardNo.substr(6, 9);
		strCardNo += this.GetVCode(strCardNo);
	}
	this.Valid = this.CheckValid(strCardNo);
};

// 校验身份证有效性
clsIDCard.prototype.IsValid = function () {
	return this.Valid;
};

// 返回生日字符串，格式如下，1981-10-10
clsIDCard.prototype.GetBirthDate = function () {
	var BirthDate = "";
	if (this.Valid) {
		BirthDate = this.GetBirthYear() + "-" + this.GetBirthMonth() + "-" + this.GetBirthDay();
	}
	return BirthDate;
};

// 返回生日中的年，格式如下，1981
clsIDCard.prototype.GetBirthYear = function () {
	var BirthYear = "";
	if (this.Valid) {
		BirthYear = this.ID18.substr(6, 4);
	}
	return BirthYear;
};

// 返回生日中的月，格式如下，10
clsIDCard.prototype.GetBirthMonth = function () {
	var BirthMonth = "";
	if (this.Valid) {
		BirthMonth = this.ID18.substr(10, 2);
	}
	if (BirthMonth.charAt(0) == "0") {
		BirthMonth = BirthMonth.charAt(1);
	}
	return BirthMonth;
};

// 返回生日中的日，格式如下，10
clsIDCard.prototype.GetBirthDay = function () {
	var BirthDay = "";
	if (this.Valid) {
		BirthDay = this.ID18.substr(12, 2);
	}
	return BirthDay;
};

// 返回性别，1：男，0：女
clsIDCard.prototype.GetSex = function () {
	var Sex = "";
	if (this.Valid) {
		Sex = this.ID18.charAt(16) % 2;
	}
	return Sex;
};

// 返回15位身份证号码
clsIDCard.prototype.Get15 = function () {
	var ID15 = "";
	if (this.Valid) {
		ID15 = this.ID15;
	}
	return ID15;
};

// 返回18位身份证号码
clsIDCard.prototype.Get18 = function () {
	var ID18 = "";
	if (this.Valid) {
		ID18 = this.ID18;
	}
	return ID18;
};

// 返回所在省，例如：上海市、浙江省
clsIDCard.prototype.GetLocal = function () {
	var Local = "";
	if (this.Valid) {
		Local = this.Local;
	}
	return Local;
};
clsIDCard.prototype.GetVCode = function (CardNo17) {
	var Wi = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1);
	var Ai = new Array("1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2");
	var cardNoSum = 0;
	for (var i = 0; i < CardNo17.length; i++) {
		cardNoSum += CardNo17.charAt(i) * Wi[i];
	}
	var seq = cardNoSum % 11;
	return Ai[seq];
};
clsIDCard.prototype.CheckValid = function (CardNo18) {
	if (this.GetVCode(CardNo18.substr(0, 17)) != CardNo18.charAt(17)) {
		return false;
	}
	if (!this.IsDate(CardNo18.substr(6, 8))) {
		return false;
	}
	var aCity = {11:"\u5317\u4eac", 12:"\u5929\u6d25", 13:"\u6cb3\u5317", 14:"\u5c71\u897f", 15:"\u5185\u8499\u53e4", 21:"\u8fbd\u5b81", 22:"\u5409\u6797", 23:"\u9ed1\u9f99\u6c5f ", 31:"\u4e0a\u6d77", 32:"\u6c5f\u82cf", 33:"\u6d59\u6c5f", 34:"\u5b89\u5fbd", 35:"\u798f\u5efa", 36:"\u6c5f\u897f", 37:"\u5c71\u4e1c", 41:"\u6cb3\u5357", 42:"\u6e56\u5317 ", 43:"\u6e56\u5357", 44:"\u5e7f\u4e1c", 45:"\u5e7f\u897f", 46:"\u6d77\u5357", 50:"\u91cd\u5e86", 51:"\u56db\u5ddd", 52:"\u8d35\u5dde", 53:"\u4e91\u5357", 54:"\u897f\u85cf ", 61:"\u9655\u897f", 62:"\u7518\u8083", 63:"\u9752\u6d77", 64:"\u5b81\u590f", 65:"\u65b0\u7586", 71:"\u53f0\u6e7e", 81:"\u9999\u6e2f", 82:"\u6fb3\u95e8", 91:"\u56fd\u5916"};
	if (aCity[parseInt(CardNo18.substr(0, 2))] == null) {
		return false;
	}
	this.ID18 = CardNo18;
	this.ID15 = CardNo18.substr(0, 6) + CardNo18.substr(8, 9);
	this.Local = aCity[parseInt(CardNo18.substr(0, 2))];
	return true;
};
clsIDCard.prototype.IsDate = function (strDate) {
	var r = strDate.match(/^(\d{1,4})(\d{1,2})(\d{1,2})$/);
	if (r == null) {
		return false;
	}
	var d = new Date(r[1], r[2] - 1, r[3]);
	return (d.getFullYear() == r[1] && (d.getMonth() + 1) == r[2] && d.getDate() == r[3]);
};