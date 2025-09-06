package com.devper.app.feature.di

import com.devper.app.feature.component.viewmodel.ProductsViewModel
import com.devper.app.feature.main.address.viewmodel.AddressViewModel
import com.devper.app.feature.main.cart.viewmodel.CartViewModel
import com.devper.app.feature.main.categories.viewmodel.CategoriesViewModel
import com.devper.app.feature.main.checkout.viewmodel.CheckoutViewModel
import com.devper.app.feature.main.detail.viewmodel.DetailViewModel
import com.devper.app.feature.main.editprofile.viewmodel.EditProfileViewModel
import com.devper.app.feature.main.home.viewmodel.HomeViewModel
import com.devper.app.feature.main.mycoupons.viewmodel.MyCouponsViewModel
import com.devper.app.feature.main.myorders.viewmodel.MyOrdersViewModel
import com.devper.app.feature.main.notifications.viewmodel.NotificationsViewModel
import com.devper.app.feature.main.paymentmethod.viewmodel.PaymentMethodViewModel
import com.devper.app.feature.main.product.viewmodel.ProductViewModel
import com.devper.app.feature.main.productdetail.viewmodel.ProductDetailViewModel
import com.devper.app.feature.main.profile.viewmodel.ProfileViewModel
import com.devper.app.feature.main.search.viewmodel.SearchViewModel
import com.devper.app.feature.main.settings.viewmodel.SettingsViewModel
import com.devper.app.feature.main.wishlist.viewmodel.WishlistViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val mainModule =
    module {
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
