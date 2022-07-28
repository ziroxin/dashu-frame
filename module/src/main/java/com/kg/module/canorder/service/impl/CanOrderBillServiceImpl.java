package com.kg.module.canorder.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kg.component.utils.BaiduOCR;
import com.kg.module.canapi.dto.CanOrderBillImageDTO;
import com.kg.module.canapi.dto.CanOrderBillTextDTO;
import com.kg.module.canorder.entity.CanOrderBill;
import com.kg.module.canorder.mapper.CanOrderBillMapper;
import com.kg.module.canorder.service.ICanOrderBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 发票表 服务实现类
 * </p>
 *
 * @author dazui
 * @since 2022-06-28
 */
@Service
public class CanOrderBillServiceImpl extends ServiceImpl<CanOrderBillMapper, CanOrderBill> implements ICanOrderBillService {

    @Autowired
    private CanOrderBillMapper canOrderBillMapper;

    @Autowired
    private BaiduOCR baiduOCR;

    @Override
    public CanOrderBill getOrderBillById(String orderId) {
        return canOrderBillMapper.getOrderBillById(orderId);
    }

    @Override
    public boolean deleteBill(List<String> orderIds) {
        return canOrderBillMapper.deleteBill(orderIds);
    }

    @Override
    public CanOrderBillTextDTO textSplit(CanOrderBillImageDTO canOrderBillImageDTO) {
        CanOrderBillTextDTO canOrderBillTextDTO = new CanOrderBillTextDTO();
        String billImage = canOrderBillImageDTO.getImage();
        JSONArray wordsResult = baiduOCR.textRecognition(billImage);

        String[] characters = {"名称", "公司全称", "税号", "纳税人识别号", "地址", "单位地址", "地址、电话", "电话", "电话号码", "开户银行", "开户行及账号", "开户行名", "银行账户", "开户账号"};

        //将JSONArray转换为List类型
        List<String> wordsList = new ArrayList<String>();
        for (int i = 0; i < wordsResult.size(); i++) {
            wordsList.add(wordsResult.getStr(i));
        }

        //通过for循环将查到的关键字下标放到subscriptList,将关键字的下标和值放到map里
        List<Integer> subscriptList = new ArrayList<Integer>();
        Map<Integer, String> subscriptMap = new HashMap<Integer, String>();
        for (int i = 0; i < wordsList.size(); i++) {
            for (int j = 0; j < characters.length; j++) {
                if (wordsList.get(i).indexOf(characters[j]) > 0) {
                    subscriptList.add(i);
                    subscriptMap.put(i, characters[j]);
                }
            }
        }

        //对subscriptList排序
        Collections.sort(subscriptList);
        //对subscriptList去重
        subscriptList = subscriptList.stream().distinct().collect(Collectors.toList());
        //对subscriptMap排序
        TreeMap subscriptTreemap = new TreeMap(subscriptMap);

        //通过for循环取出wordsList中含有关键字的数据放入billText
        List<String> billText = new ArrayList<String>();
        for (int i = 0; i < subscriptList.size(); i++) {
            int j = i + 1;
            if (j < subscriptList.size()) {
                billText.add(wordsList.subList(subscriptList.get(i), subscriptList.get(j)).toString());
            } else {
                billText.add(wordsList.get(subscriptList.get(i)));
            }
        }

        List<String> billTextList = new ArrayList<>();
        //遍历billText,删除多余字符后,将符合要求的数据赋值
        for (int i = 0; i < billText.size(); i++) {
            if (billText.get(i).indexOf("公司") > 0 && billText.get(i).indexOf("开户") < 0) {
                canOrderBillTextDTO.setCompanyName(cropTest(billText.get(i), subscriptTreemap.get(subscriptList.get(i)).toString()));
            } else if (billText.get(i).indexOf("税") > 0 && billText.get(i).indexOf("号") > 0) {
                canOrderBillTextDTO.setTaxId(cropTest(billText.get(i), subscriptTreemap.get(subscriptList.get(i)).toString()));
            } else if (billText.get(i).indexOf("地址") > 0) {
                canOrderBillTextDTO.setCompanyAdress(cropTest(billText.get(i), subscriptTreemap.get(subscriptList.get(i)).toString()));
            } else if (billText.get(i).indexOf("电话") > 0) {
                canOrderBillTextDTO.setCompanyPhone(cropTest(billText.get(i), subscriptTreemap.get(subscriptList.get(i)).toString()));
            } else if (billText.get(i).indexOf("银行") > 0 && billText.get(i).indexOf("账户") < 0 && billText.get(i).indexOf("账号") < 0) {
                canOrderBillTextDTO.setBank(cropTest(billText.get(i), subscriptTreemap.get(subscriptList.get(i)).toString()));
            }else if (billText.get(i).indexOf("账户") > 0 || billText.get(i).indexOf("账号") > 0){
                canOrderBillTextDTO.setBankAccount(cropTest(billText.get(i), subscriptTreemap.get(subscriptList.get(i)).toString()));
            }
            billTextList.add(cropTest(billText.get(i), subscriptTreemap.get(subscriptList.get(i)).toString()));
        }
        canOrderBillTextDTO.setTextData(billTextList);
        return canOrderBillTextDTO;
    }

    public String cropTest(String test, String characters) {
        String replaceData1 = test.replace("：", ":");
        String replaceData2 = replaceData1.replace("，", ",");
        String replaceData3 = replaceData2.replace(characters, "");
        String data = StrUtil.removeAll(replaceData3, ':', '{', 'w', 'o', 'r', 'd', 's', '}', '"', ',',' ','[',']');
        return data;
    }
}
