# 电信收费问题的实现类
class TelecomCharge:
    BASE_FEE = 25  # 基本月租费
    FEE_PER_MIN = 0.15  # 每分钟通话费

    call_time = 0  # 本月通话时间
    missed_charges = 0  # 本年度累计未按时缴费的次数
    telecom_charge = 0  # 本月应交的通话费金额

    def __init__(self, call_time, missed_charges):
        self.call_time = call_time
        self.missed_charges = missed_charges

    # 计算通话费金额的方法
    def compute_charge(self):
        if self.call_time < 0:
            return "通话时间不能为负"
        if self.call_time > 31*24*60:
            return "通话时间超出范围"
        if self.missed_charges < 0:
            return "累计未按时缴费的次数不能为负"
        if self.missed_charges > 11:
            return "累计未按时缴费的次数超出范围"
        # 计算无折扣金额
        original_charge = self.BASE_FEE + self.FEE_PER_MIN * self.call_time
        # 根据通话时间和未按时缴费的次数判断折扣率
        discount_rate = 0
        if self.call_time <= 60:
            if self.missed_charges <= 1:
                discount_rate = 1.0
        elif self.call_time <= 120:
            if self.missed_charges <= 2:
                discount_rate = 1.5
        elif self.call_time <= 180:
            if self.missed_charges <= 3:
                discount_rate = 2.0
        elif self.call_time <= 300:
            if self.missed_charges <= 3:
                discount_rate = 2.5
        else:
            if self.missed_charges <= 6:
                discount_rate = 3.0
        # 计算折扣后的金额并返回
        self.telecom_charge = original_charge * (1 - discount_rate / 100)
        return self.telecom_charge


if __name__ == "__main__":
    charge = TelecomCharge(200,2)
    print(charge.compute_charge())