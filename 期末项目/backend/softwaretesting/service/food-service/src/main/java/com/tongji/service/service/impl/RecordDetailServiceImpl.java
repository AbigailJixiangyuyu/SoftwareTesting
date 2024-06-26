package com.tongji.service.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tongji.model.dto.RecordDetailAddDTO;
import com.tongji.model.dto.RecordDetailDTO;
import com.tongji.model.dto.RecordDetailReturnDTO;
import com.tongji.model.pojo.Record;
import com.tongji.model.pojo.RecordDetail;
import com.tongji.model.vo.ResponseResult;
import com.tongji.service.mapper.FoodMapper;
import com.tongji.service.mapper.RecordDetailMapper;
import com.tongji.service.service.IRecordDetailService;
import com.tongji.service.service.IRecordService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;


/**
 * <p>
 * 预测信息细节 服务实现类
 * </p>
 *
 * @author author
 * @since 2023-11-29
 */
@Service
public class RecordDetailServiceImpl extends ServiceImpl<RecordDetailMapper, RecordDetail> implements IRecordDetailService {

    @Autowired
    @Lazy
    private IRecordService recordService;

    @Autowired
    private FoodMapper foodMapper;

    @Override
    public ResponseResult getById(Long id) {
        if (id == null) {
            return ResponseResult.errorResult(400, "id不能为空");
        }
        Long userId = StpUtil.getLoginIdAsLong();

        RecordDetail recordDetail = this.getOne(
                Wrappers.<RecordDetail>lambdaQuery().eq(RecordDetail::getId, id)
        );

        if (recordDetail == null) {
            return ResponseResult.errorResult(400, "该饮食详细数据不存在");
        }

        Record record = recordService.getOne(
                Wrappers.<Record>lambdaQuery().eq(Record::getUserId, userId).
                        eq(Record::getId, recordDetail.getRecordId())
        );
        if(record == null) {
            return ResponseResult.errorResult(400, "饮食详细记录不属于当前用户或不存在");
        }

        return ResponseResult.okResult(recordDetail);
    }

    @Override
    public ResponseResult getByRecordId(Long record_id) {
        if (record_id == null) {
            return ResponseResult.errorResult(400, "record_id不能为空");
        }
        Long userId = StpUtil.getLoginIdAsLong();

        Record record = recordService.getOne(
                Wrappers.<Record>lambdaQuery().eq(Record::getUserId, userId).
                        eq(Record::getId, record_id)
        );
        if(record == null) {
            return ResponseResult.errorResult(400, "饮食详细记录不属于当前用户或不存在");
        }

        List<RecordDetail> recordDetailList = this.list(
                Wrappers.<RecordDetail>lambdaQuery().eq(RecordDetail::getRecordId, record_id)
        );

        if(recordDetailList == null) {
            return ResponseResult.errorResult(400, "该饮食详细数据不存在");
        }

        List<RecordDetailReturnDTO> recordDetailReturnDTOList = new ArrayList<>();
        for(RecordDetail recordDetail : recordDetailList) {
            RecordDetailReturnDTO recordDetailReturnDTO = new RecordDetailReturnDTO();
            BeanUtils.copyProperties(recordDetail, recordDetailReturnDTO);
            String foodName = foodMapper.selectById(recordDetail.getFoodId()).getName();
            recordDetailReturnDTO.setFoodName(foodName);
            recordDetailReturnDTOList.add(recordDetailReturnDTO);
        }

        return ResponseResult.okResult(recordDetailReturnDTOList);
    }

    @Override
    public ResponseResult deleteRecordDetail(Long id) {
        if (id == null) {
            return ResponseResult.errorResult(400, "id不能为空");
        }
        Long userId = StpUtil.getLoginIdAsLong();
        RecordDetail recordDetail = this.getOne(
                Wrappers.<RecordDetail>lambdaQuery().eq(RecordDetail::getId, id)
        );

        if (recordDetail == null) {
            return ResponseResult.errorResult(400, "该饮食详细数据不存在");
        }

        Record record = recordService.getOne(
                Wrappers.<Record>lambdaQuery().eq(Record::getUserId, userId).
                        eq(Record::getId, recordDetail.getRecordId())
        );
        if(record == null) {
            return ResponseResult.errorResult(400, "饮食详细记录不属于当前用户或不存在");
        }

        this.removeById(id);
        return ResponseResult.okResult("删除成功");
    }

    @Override
    public ResponseResult updateRecordDetail(RecordDetailDTO recordDetailDTO) {
        if (recordDetailDTO.getId() == null|| recordDetailDTO.getRecordId() == null || recordDetailDTO.getFoodId() == null) {
            return ResponseResult.errorResult(400, "id不能为空");
        }
        Long userId = StpUtil.getLoginIdAsLong();
        RecordDetail recordDetail = this.getOne(
                Wrappers.<RecordDetail>lambdaQuery().eq(RecordDetail::getId, recordDetailDTO.getId())
        );
        if (recordDetail == null) {
            return ResponseResult.errorResult(400, "该饮食详细数据不存在");
        }

        Record record = recordService.getOne(
                Wrappers.<Record>lambdaQuery().eq(Record::getUserId, userId).
                        eq(Record::getId, recordDetail.getRecordId())
        );
        if(record == null) {
            return ResponseResult.errorResult(400, "饮食详细记录不属于当前用户或不存在");
        }

        BeanUtils.copyProperties(recordDetailDTO, recordDetail);
        this.updateById(recordDetail);
        return ResponseResult.okResult("修改成功");
    }

    @Override
    public ResponseResult addRecordDetail(RecordDetailAddDTO recordDetailAddDTO) {
        if (recordDetailAddDTO.getRecordId() == null || recordDetailAddDTO.getFoodId() == null) {
            return ResponseResult.errorResult(400, "食物和所属记录不能为空");
        }
        Long userId = StpUtil.getLoginIdAsLong();

        RecordDetail recordDetail = new RecordDetail();
        BeanUtils.copyProperties(recordDetailAddDTO, recordDetail);
        this.save(recordDetail);
        return ResponseResult.okResult("添加成功");
    }
}
