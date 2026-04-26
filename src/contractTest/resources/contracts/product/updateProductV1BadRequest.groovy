package contracts.product

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    request {
        method PUT()
        headers {
            accept 'application/json'
            contentType 'application/json'
        }
        urlPath("/api/v1/products/fffe6ec2-7103-48b3-8e4f-3b58e43fb75a") {
            body([
                name: " "
            ])
        }
    }
    response {
        status 400
        headers {
            contentType 'application/problem+json'
        }
        body([
            instance: fromRequest().path(),
            type: "/errors/invalid-fields",
            title: "Invalid fields",
            detail: "One or more fields are invalid",
            fields: [
                name: anyNonBlankString(),
                brand: anyNonBlankString(),
                regularPrice: anyNonBlankString(),
                salePrice: anyNonBlankString(),
                enabled: anyNonBlankString(),
                categoryId: anyNonBlankString(),
            ]
        ])
    }

}