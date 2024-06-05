
# 销售系统实现类
class SaleSystem:
    def __init__(self, sales, account, day):
        self.sales = sales
        self.account = account
        self.day = day

    def compute_money(self):
        if self.sales < 0 or self.account < 0 or self.account > 1 or self.day < 0 or self.day > 366:
            return -1

        if self.sales > 200 and self.day <= 10:
            if self.account >= 0.6:
                return self.sales / 7.0
            else:
                return 0

        if self.account <= 0.85:
            return self.sales / 6.0
        else:
            return self.sales / 5.0
