from flask import Flask
import pandas as pd
from flask_cors import cross_origin
from pandas import DataFrame
from flask import Flask, request, make_response
import os
import json
import uuid
from service.charge import TelecomCharge
from service.sale_system import SaleSystem
from service.computer_sales_system import ComputerSalesSystem

app = Flask(__name__)


# 两个公共处理函数
def dealFile(file):
    file_path = os.getcwd() + '/result'
    if not os.path.exists(file_path):
        # 如果目录不存在，则创建目录
        os.makedirs(file_path)
    file_path += '/' + str(uuid.uuid4()) + '.xlsx'
    file.save(file_path)
    # 使用pandas库读取excel表格中的测试数据
    return pd.read_excel(file_path), file_path


def dealResponse(df, file_path):
    # 将结果写入文件
    DataFrame(df).to_excel(file_path, sheet_name='Sheet1', index=False, header=True)
    # 将结果以json形式返回给前端
    dic = df.to_dict(orient='records')
    my_dict = {'file': file_path, 'result': dic}
    result = json.dumps(my_dict)
    response = make_response(result)
    response.headers["Content-Type"] = "application/json"
    return response


# 用来测试电信收费问题的api
@app.route('/test/charge', methods=['POST', 'GET'])
@cross_origin()
def charge():
    df, file_path = dealFile(request.files['file'])
    for i in range(df.shape[0]):
        # 调用被测试的方法，获取实际输出
        tele_charge = TelecomCharge(df.loc[i, 'minute'], df.loc[i, 'times'])
        real = tele_charge.compute_charge()
        df.loc[i, 'real'] = real
        if df.loc[i, 'real'] != df.loc[i, 'expect']:
            result = "未通过测试"
        else:
            result = "通过测试"
        df.loc[i, 'result'] = result
    return dealResponse(df, file_path)


# 用来销售系统问题的api
@app.route('/test/sale_system', methods=['POST', 'GET'])
@cross_origin()
def sale_system():
    df, file_path = dealFile(request.files['file'])
    for i in range(df.shape[0]):
        # 调用被测试的方法，获取实际输出
        sale_system = SaleSystem(df.loc[i, 'sales'], df.loc[i, 'account'], df.loc[i, 'day'])
        real = sale_system.compute_money()
        df.loc[i, 'real'] = real
        if df.loc[i, 'real'] != df.loc[i, 'expect']:
            result = "未通过测试"
        else:
            result = "通过测试"
        df.loc[i, 'result'] = result
    return dealResponse(df, file_path)


# 处理电脑销售系统的api
@app.route('/test/commission', methods=['POST', 'GET'])
@cross_origin()
def commission():
    df, file_path = dealFile(request.files['file'])
    for i in range(df.shape[0]):
        computer_sales_system = ComputerSalesSystem(df.loc[i, 'mainframes'], df.loc[i, 'monitors'], df.loc[i, 'peripherals'])
        real = computer_sales_system.compute_commission()
        df.loc[i, 'real'] = real
        if df.loc[i, 'real'] == df.loc[i, 'expect']:
            result = "通过测试"
        else:
            result = "未通过测试"
        df.loc[i, 'result'] = result
    return dealResponse(df, file_path)


@app.route('/ping')
def ping_pong():
    return 'pong'


if __name__ == '__main__':
    app.run(port=25691)
