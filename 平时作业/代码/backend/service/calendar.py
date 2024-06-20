class Calendar:
    def __init__(self, year, month, day):
        self.year = year
        self.month = month
        self.day = day

    def compute_next_day(self):
        if 1800 <= self.year <= 2200 and 1 <= self.month <= 12 and 1 <= self.day <= 31:
            if self.month in [1, 3, 5, 7, 8, 10]:
                if 1 <= self.day <= 30:
                    self.day = self.day + 1
                else:
                    self.day = 1
                    self.month = self.month + 1
            elif self.month in [4, 6, 9, 11]:
                if 1 <= self.day <= 29:
                    self.day = self.day + 1
                elif self.day == 30:
                    self.day = 1
                    self.month = self.month + 1
                else:
                    return "日期不存在"
            elif self.month == 12:
                if 1 <= self.day <= 30:
                    self.day = self.day + 1
                else:
                    self.day = 1
                    self.month = 1
                    self.year = self.year + 1
            elif self.month == 2:
                if self.year % 4 == 0 and self.year % 100 != 0 or self.year % 400 == 0:
                    if 1 <= self.day <= 28:
                        self.day = self.day + 1
                    elif self.day == 29:
                        self.day = 1
                        self.month = self.month + 1
                    else:
                        return "日期不存在"
                else:
                    if 1 <= self.day <= 27:
                        self.day = self.day + 1
                    elif self.day == 28:
                        self.day = 1
                        self.month = self.month + 1
                    else:
                        return "日期不存在"

        else:
            return "日期不存在"
        return str(self.year) + '/' + str(self.month) + '/' + str(self.day)
