package com.devper.app.feature.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import presentation.ui.main.address.view_model.AddressViewModel
import presentation.ui.main.cart.view_model.CartViewModel
import presentation.ui.main.categories.view_model.CategoriesViewModel
import presentation.ui.main.checkout.view_model.CheckoutViewModel
import com.devper.app.feature.main.product_detail.viewmodel.ProductDetailViewModel
import com.devper.app.feature.main.edit_profile.viewmodel.EditProfileViewModel
import com.devper.app.feature.main.home.viewmodel.HomeViewModel
import presentation.ui.main.my_coupons.view_model.MyCouponsViewModel
import presentation.ui.main.my_orders.view_model.MyOrdersViewModel
import presentation.ui.main.notifications.view_model.NotificationsViewModel
import presentation.ui.main.payment_method.view_model.PaymentMethodViewModel
import com.devper.app.feature.main.profile.viewmodel.ProfileViewModel
import com.devper.app.feature.main.search.viewmodel.SearchViewModel
import com.devper.app.feature.main.settings.viewmodel.SettingsViewModel
import com.devper.app.feature.main.wishlist.viewmodel.WishlistViewModel
import com.devper.app.feature.component.viewmodel.ProductsViewModel
import com.devper.app.feature.main.product.viewmodel.ProductViewModel
import com.devper.app.feature.main.detail.viewmodel.DetailViewModel


val mainModule = module {
    factoryOf(::HomeViewModel)
    factoryOf(::ProductViewModel)
    factoryOf(::ProductsViewModel)
    factoryOf(::ProductDetailViewModel)
    factoryOf(::AddressViewModel)
    factoryOf(::CategoriesViewModel)
    factoryOf(::ProfileViewModel)
    factoryOf(::SettingsViewModel)
    factoryOf(::EditProfileViewModel)
    factoryOf(::PaymentMethodViewModel)
    factoryOf(::NotificationsViewModel)
    factoryOf(::MyCouponsViewModel)
    factoryOf(::MyOrdersViewModel)
    factoryOf(::CheckoutViewModel)
    factoryOf(::WishlistViewModel)
    factoryOf(::CartViewModel)
    factoryOf(::DetailViewModel)
    factoryOf(::SearchViewModel)
}