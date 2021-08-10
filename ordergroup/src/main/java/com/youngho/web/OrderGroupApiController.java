package com.youngho.web;

import com.youngho.service.OrderGroupService;
import com.youngho.web.dto.request.OrderChangeRequestDto;
import com.youngho.web.dto.request.OrderGroupAddRequestDto;
import com.youngho.web.dto.response.OrderGroupListResponseDto;
import com.youngho.web.dto.response.OrderGroupResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "${ordergroup.api}", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderGroupApiController {

    private final OrderGroupService orderGroupService;

    @PostMapping("/orders")
    public ResponseEntity<Long> orderProduct(@RequestBody OrderGroupAddRequestDto requestDto) {
        return new ResponseEntity<>(orderGroupService.orderProduct(requestDto), HttpStatus.OK);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderGroupListResponseDto>> getOrders() {
        return new ResponseEntity<>(orderGroupService.getOrders(), HttpStatus.OK);
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<OrderGroupResponseDto> getOrderById(@PathVariable Long id) {
        return new ResponseEntity<>(orderGroupService.getOrderById(id), HttpStatus.OK);
    }

    @PutMapping("/orders/{id}")
    public ResponseEntity<Long> changeOrder(@PathVariable Long id, @RequestBody OrderChangeRequestDto requestDto) {
        return new ResponseEntity<>(orderGroupService.changeOrder(id, requestDto), HttpStatus.OK);
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<Long> deleteOrderById(@PathVariable Long id) {
        return new ResponseEntity<>(orderGroupService.deleteOrderById(id), HttpStatus.OK);
    }
}
