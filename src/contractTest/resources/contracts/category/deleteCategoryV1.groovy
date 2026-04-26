package contracts.category

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method DELETE()
        urlPath("/api/v1/categories/a3c91d7f-52be-4e8a-b017-9f2e847d1c34")
    }
    response {
        status 204
    }
}