package com.tangl.wiki.controller;

import com.tangl.wiki.po.EbookPO;
import com.tangl.wiki.response.CommonResponse;
import com.tangl.wiki.service.EbookService;
import com.tangl.wiki.vo.EbookVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author tangl
 * @description
 * @create 2023-08-23 22:34
 */
@RestController
@RequestMapping("/ebook")
public class EbookController {
    @Resource
    private EbookService ebookService;

    @GetMapping("/list")
    public CommonResponse<List<EbookVO>> list(EbookPO ebookPO) {
        CommonResponse<List<EbookVO>> commonResponse = new CommonResponse<>();
        List<EbookVO> list = ebookService.list(ebookPO);
        commonResponse.setData(list);
        return commonResponse;
    }
}
