package com.vtluan.place_order_football.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vtluan.place_order_football.model.Cart;
import com.vtluan.place_order_football.model.CartDetail;
import com.vtluan.place_order_football.model.FootballField;
import com.vtluan.place_order_football.model.FootballFieldChild;
import com.vtluan.place_order_football.model.FootballFieldChildAndTimeFrame;
import com.vtluan.place_order_football.model.TimeFrame;
import com.vtluan.place_order_football.model.Users;
import com.vtluan.place_order_football.model.dto.request.ReqFootballFiledChildAndTimeFrame;
import com.vtluan.place_order_football.model.dto.response.ResCart;
import com.vtluan.place_order_football.model.dto.response.ResFootballField;
import com.vtluan.place_order_football.model.dto.response.ResponseDto;
import com.vtluan.place_order_football.service.CartDetailService;
import com.vtluan.place_order_football.service.CartService;
import com.vtluan.place_order_football.service.FootballFieldChildAndTimeFrameService;
import com.vtluan.place_order_football.service.FootballFieldService;
import com.vtluan.place_order_football.service.UserService;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartervice;
    private final UserService userService;
    private final CartDetailService cartDetailService;
    private final FootballFieldChildAndTimeFrameService footballFieldChildAndTimeFrameService;

    @PostMapping("")
    public ResponseEntity<ResponseDto<Void>> addToCart(
            @RequestBody ReqFootballFiledChildAndTimeFrame reqFootballFiledChildAndTimeFrame) {
        this.cartervice.addToCart(reqFootballFiledChildAndTimeFrame);

        ResponseDto<Void> responseDto = new ResponseDto<>();
        responseDto.setMessenger("add to cart success");
        responseDto.setStatus(HttpStatus.OK.value());
        return ResponseEntity.ok().body(responseDto);
    }

    @GetMapping("")
    public ResponseEntity<ResponseDto<ResCart>> getCartDetais(@RequestParam String email) {
        Users user = this.userService.getUserByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("user not found!!!");
        }
        ResCart resCart = this.cartervice.fetchCartByUser(user);
        ResponseDto<ResCart> responseDto = new ResponseDto<>();

        responseDto.setStatus(HttpStatus.OK.value());
        responseDto.setError(null);
        responseDto.setData(resCart);
        responseDto.setMessenger("Call Api Successful");
        return ResponseEntity.ok().body(responseDto);
    }

    // delete cart_detail

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteCartDetail(@PathVariable long id) {
        Optional<CartDetail> cartDetail = this.cartDetailService.getCartDetailById(id);
        if (cartDetail.isPresent()) {
            FootballFieldChildAndTimeFrame fieldChildAndTimeFrame = cartDetail.get()
                    .getFootballFieldChildAndTimeFrame();
            fieldChildAndTimeFrame.setIsBooked(false);
            this.footballFieldChildAndTimeFrameService.generatedFieldAndTimeFrame(fieldChildAndTimeFrame);
            this.cartDetailService.deleteCartDetail(cartDetail.get());
        }
        return ResponseEntity.noContent().build();
    }

}
