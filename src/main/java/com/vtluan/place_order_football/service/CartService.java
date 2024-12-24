package com.vtluan.place_order_football.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vtluan.place_order_football.model.Cart;
import com.vtluan.place_order_football.model.CartDetail;
import com.vtluan.place_order_football.model.FootballField;
import com.vtluan.place_order_football.model.FootballFieldChild;
import com.vtluan.place_order_football.model.FootballFieldChildAndTimeFrame;
import com.vtluan.place_order_football.model.TimeFrame;
import com.vtluan.place_order_football.model.Users;
import com.vtluan.place_order_football.model.dto.request.ReqFootballFiledChildAndTimeFrame;
import com.vtluan.place_order_football.model.dto.response.ResCart;
import com.vtluan.place_order_football.repository.CartRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {

    private final FootballFieldChildAndTimeFrameService footballFieldChildAndTimeFrameService;
    private final CartDetailService cartDetailService;
    private final CartRepository cartRepository;
    private final FootballFieldService footballFieldService;
    private final UserService userService;

    public List<Cart> getAllcarts() {
        return this.cartRepository.findAll();
    }

    public Optional<Cart> getcartById(long id) {
        return this.cartRepository.findById(id);
    }

    public Cart saveOrUpdate(Cart cart) {

        return this.cartRepository.save(cart);
    }

    public void deletecartById(long id) {
        this.cartRepository.deleteById(id);
    }

    public Cart getCartByUser(Users users) {
        return this.cartRepository.findByUser(users);
    }

    public void addToCart(ReqFootballFiledChildAndTimeFrame item) {

        Users users = this.userService.getUserByEmail(item.getEmail());

        if (users == null) {
            throw new UsernameNotFoundException("không tồn tại người dùng với emai " + item.getEmail());
        }

        // check cart has exist
        Cart currentCart = this.getCartByUser(users);
        // else create cart
        if (currentCart == null) {
            Cart newCart = new Cart();
            newCart.setUser(users);
            newCart.setTotal(0);
            currentCart = this.saveOrUpdate(newCart);
        }
        // find footfieldchid and time then create cartdetail and push to
        // lChildAndTimeFrames
        // set booked = true
        for (TimeFrame timeFrame : item.getTimeframes()) {
            FootballFieldChildAndTimeFrame footballFieldChildAndTimeFrame = this.footballFieldChildAndTimeFrameService
                    .getByTimeFrameAndFootballFieldChild(
                            timeFrame,
                            item.getFootfieldChild());
            if (footballFieldChildAndTimeFrame != null) {

                Optional<FootballField> footballField = this.footballFieldService.getById(item.getIdFootField());
                if (footballField.isPresent()) {
                    footballFieldChildAndTimeFrame.setIsBooked(true);
                    this.footballFieldChildAndTimeFrameService
                            .generatedFieldAndTimeFrame(footballFieldChildAndTimeFrame);
                    CartDetail cartDetail = new CartDetail();
                    cartDetail.setCart(currentCart);
                    cartDetail.setFootballFieldChildAndTimeFrame(footballFieldChildAndTimeFrame);
                    cartDetail.setPrice(footballField.get().getPrice());
                    this.cartDetailService.createCartDetail(cartDetail);
                    currentCart.setTotal(currentCart.getTotal() >= 0 ? currentCart.getTotal() + 1 : 1);
                    this.saveOrUpdate(currentCart);

                    List<Boolean> listStatus = new ArrayList<>();
                    List<FootballFieldChild> fieldChilds = footballField.get().getFootballFieldChilds();
                    for (FootballFieldChild fieldChild : fieldChilds) {
                        List<FootballFieldChildAndTimeFrame> fieldChildAndTimeFrames = fieldChild
                                .getFootballFieldChildAndTimeFrames();
                        for (FootballFieldChildAndTimeFrame fieldChildAndTimeFrame : fieldChildAndTimeFrames) {
                            listStatus.add(fieldChildAndTimeFrame.getIsBooked());
                        }
                    }

                    Boolean checkFull = listStatus.stream().allMatch(check -> check == true);

                    if (checkFull) {
                        footballField.get().setStatus(false);
                        this.footballFieldService.updateFootballField(footballField.get());
                    }
                }

            }
        }
    }

    public ResCart fetchCartByUser(Users user) {
        ResCart resCart = new ResCart();
        List<ResCart.ResCartDetail> resCartDetails = new ArrayList<>();
        Cart cart = user.getCart() != null ? user.getCart() : null;
        if (cart != null) {
            List<CartDetail> cartDetails = cart.getCartDetails();
            for (CartDetail item : cartDetails) {
                FootballFieldChildAndTimeFrame fieldChildAndTimeFrame = item.getFootballFieldChildAndTimeFrame();
                TimeFrame timeFrame = fieldChildAndTimeFrame.getTimeFrame();
                FootballFieldChild footballFieldChild = fieldChildAndTimeFrame.getFootballFieldChild();

                resCart.setDay(LocalDate.now());
                resCart.setTotal(cart.getTotal());
                ResCart.ResCartDetail resCartDetail = new ResCart.ResCartDetail();
                resCartDetail.setFieldChildName(footballFieldChild.getNameField());
                resCartDetail.setFieldName(footballFieldChild.getFootballField().getName());
                resCartDetail.setTimeFrame(timeFrame.getTimeDes());
                resCartDetail.setId(item.getId());
                resCartDetail.setPrice(footballFieldChild.getFootballField().getPrice());

                resCartDetails.add(resCartDetail);

                resCart.setResCartDetails(resCartDetails);
            }
            return resCart;
        }
        return null;

    }

    @Transactional
    @Scheduled(cron = "0 0 0 * * ?")
    void resetTotolCart() {
        List<Cart> carts = this.getAllcarts();
        for (Cart item : carts) {
            item.setTotal(0);
            this.saveOrUpdate(item);
        }

    }
}
