package com.mustfaibra.shoesstore.repositories

import com.mustfaibra.shoesstore.data.local.RoomDao
import com.mustfaibra.shoesstore.models.Advertisement
import com.mustfaibra.shoesstore.models.Manufacturer
import com.mustfaibra.shoesstore.sealed.DataResponse
import com.mustfaibra.shoesstore.sealed.Error
import com.mustfaibra.shoesstore.utils.getStructuredManufacturers
import javax.inject.Inject

class BrandsRepository @Inject constructor(
    private val dao: RoomDao,
) {
    suspend fun getBrandsAdvertisements(): DataResponse<List<Advertisement>> {
        /** First we should check the local storage */
        dao.getAdvertisements().let {
            if (it.isNotEmpty()) {
                return DataResponse.Success(data = it)
            } else {
                /** Now we should fetch from the remote server */
                return DataResponse.Error(error = Error.Empty)
            }
        }
    }

    suspend fun getBrandsWithProducts(): DataResponse<List<Manufacturer>> {
        /** First we should check the local storage */
        dao.getManufacturersWithProducts().getStructuredManufacturers().let {
            if (it.isNotEmpty()) {
                return DataResponse.Success(data = it)
            } else {
                /** Now we should fetch from the remote server */
                return DataResponse.Error(error = Error.Empty)
            }
        }
    }
}

