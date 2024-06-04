from flask import Flask
import pandas as pd
from flask_cors import cross_origin
from pandas import DataFrame
from flask import Flask, request, make_response
import os
import json
from service.charge import TelecomCharge

app = Flask(__name__)

# 用来测试电信收费问题的api
@app.route('/test/charge', methods=['POST', 'GET'])
@cross_origin()
def charge():
    test_file = request.files['file']
    file_path = os.getcwd() + '/' + test_file.filename
    test_file.save(file_path)
    # 使用pandas库读取excel表格中的测试数据
    df = pd.read_excel(test_file.filename)
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
    # 将结果写入文件
    DataFrame(df).to_excel(file_path, sheet_name='Sheet1', index=False, header=True)
    # 将结果以json形式返回给前端
    result = json.dumps(df.to_dict(orient='records'))
    response = make_response(result)
    return response


if __name__ == '__main__':
    app.run()
