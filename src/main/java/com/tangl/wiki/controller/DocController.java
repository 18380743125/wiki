package com.tangl.wiki.controller;

import com.tangl.wiki.po.DocQueryPO;
import com.tangl.wiki.po.DocSavePO;
import com.tangl.wiki.response.CommonResponse;
import com.tangl.wiki.service.DocService;
import com.tangl.wiki.vo.DocQueryVO;
import com.tangl.wiki.vo.PageVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tangl
 * @description
 * @create 2023-08-27 8:58
 */
@RestController
@RequestMapping("/doc")
public class DocController {
    @Resource
    private DocService docService;

    @GetMapping("/list")
    public CommonResponse<PageVO<DocQueryVO>> list(@Valid DocQueryPO docQueryPO) {
        CommonResponse<PageVO<DocQueryVO>> commonResponse = new CommonResponse<>();
        PageVO<DocQueryVO> list = docService.list(docQueryPO);
        commonResponse.setContent(list);
        return commonResponse;
    }

    @GetMapping("/all/{ebookId}")
    public CommonResponse<List<DocQueryVO>> all(@PathVariable Long ebookId) {
        CommonResponse<List<DocQueryVO>> commonResponse = new CommonResponse<>();
        List<DocQueryVO> list = docService.all(ebookId);
        commonResponse.setContent(list);
        return commonResponse;
    }

    @PostMapping("/save")
    public CommonResponse<?> save(@RequestBody @Valid DocSavePO docSavePO) {
        CommonResponse<?> commonResponse = new CommonResponse<>();
        docService.save(docSavePO);
        return commonResponse;
    }

    @DeleteMapping("/delete/{ids}")
    public CommonResponse<?> delete(@PathVariable String ids) {
        CommonResponse<?> commonResponse = new CommonResponse<>();
        List<Long> list = Arrays.stream(ids.split(",")).map(s -> Long.valueOf(s.trim())).collect(Collectors.toList());
        docService.delete(list);
        return commonResponse;
    }

    @GetMapping("/find-content/{id}")
    public CommonResponse<String> findContent(@PathVariable Long id) {
        CommonResponse<String> commonResponse = new CommonResponse<>();
        String content = docService.findContent(id);
        commonResponse.setContent(content);
        return commonResponse;
    }
}
