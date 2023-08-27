package com.tangl.wiki.controller;

import com.tangl.wiki.po.EbookQueryPO;
import com.tangl.wiki.po.EbookSavePO;
import com.tangl.wiki.response.CommonResponse;
import com.tangl.wiki.service.EbookService;
import com.tangl.wiki.vo.EbookQueryVO;
import com.tangl.wiki.vo.PageVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

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
    public CommonResponse<PageVO<EbookQueryVO>> list(@Valid EbookQueryPO ebookPO) {
        CommonResponse<PageVO<EbookQueryVO>> commonResponse = new CommonResponse<>();
        PageVO<EbookQueryVO> list = ebookService.list(ebookPO);
        commonResponse.setData(list);
        return commonResponse;
    }

    @PostMapping("/save")
    public CommonResponse<?> save(EbookSavePO ebookSavePO) {
        CommonResponse<?> commonResponse = new CommonResponse<>();
        ebookService.save(ebookSavePO);
        return commonResponse;
    }

    @DeleteMapping("/delete/{id}")
    public CommonResponse<?> delete(@PathVariable Long id) {
        CommonResponse<?> commonResponse = new CommonResponse<>();
        ebookService.delete(id);
        return commonResponse;
    }
}
