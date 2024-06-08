class ComputerSalesSystem:
    PRICE_MAINFRAME = 25
    PRICE_MONITOR = 30
    PRICE_PERIPHERAL = 45

    def __init__(self, mainframes, monitors, peripherals):
        self.mainframes = mainframes
        self.monitors = monitors
        self.peripherals = peripherals

    def compute_commission(self):
        if self.mainframes == -1:
            return self._calculate_commission()
        if self.mainframes < 1 or self.monitors < 1 or self.peripherals < 1:
            return -1
        gross_sales = (self.mainframes * self.PRICE_MAINFRAME +
                       self.monitors * self.PRICE_MONITOR +
                       self.peripherals * self.PRICE_PERIPHERAL)
        return self._calculate_commission(gross_sales)

    @staticmethod
    def _calculate_commission(gross_sales=0):
        if gross_sales <= 1000:
            return gross_sales * 0.10
        elif 1000 < gross_sales <= 1800:
            return gross_sales * 0.15
        else:
            return gross_sales * 0.20
