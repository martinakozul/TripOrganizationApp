package com.camunda.triporganization.ui.components.forms

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.camunda.triporganization.model.PartnerOfferItem
import com.camunda.triporganization.ui.components.CustomTopBar

@Composable
fun PartnerOfferReviewScreen(
    transportOffers: List<PartnerOfferItem>,
    accommodationOffers: List<PartnerOfferItem>,
    onOffersAccepted: (PartnerOfferItem, PartnerOfferItem) -> Unit,
    onOffersRejected: () -> Unit,
    onBackPressed: () -> Unit,
    modifier: Modifier = Modifier
) {
    var transport by remember { mutableStateOf<PartnerOfferItem?>(null) }
    var accommodation by remember { mutableStateOf<PartnerOfferItem?>(null) }

    Scaffold(
        topBar = {
            CustomTopBar(
                title = "Pick offer",
                onBackPressed = onBackPressed
            )
        }
    )  { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxWidth()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(2.dp, shape = RoundedCornerShape(8.dp))
                    .background(
                        MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(vertical = 8.dp, horizontal = 16.dp)
            )
            {
                Text(
                    style = MaterialTheme.typography.bodyLarge,
                    text = "Transport offers"
                )

                transportOffers.forEachIndexed { i, offer ->
                    Row(
                        modifier = Modifier
                            .clickable {
                                transport = offer
                            }
                            .background(
                                if (offer == transport) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    MaterialTheme.colorScheme.primaryContainer
                                }
                            )
                            .padding(vertical = 8.dp, horizontal = 4.dp)
                    ) {
                        Text(
                            style = MaterialTheme.typography.bodyMedium,
                            text = offer.partnerName,
                            color = if (offer == transport) {
                                MaterialTheme.colorScheme.onPrimary
                            } else {
                                MaterialTheme.colorScheme.onSurface
                            }
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            style = MaterialTheme.typography.labelLarge,
                            text = offer.pricePerPerson.toString(),
                            color = if (offer == transport) {
                                MaterialTheme.colorScheme.onPrimary
                            } else {
                                MaterialTheme.colorScheme.onSurface
                            }
                        )
                    }
                    if (i != transportOffers.lastIndex) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(1.dp)
                                .background(Color.LightGray)
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(2.dp, shape = RoundedCornerShape(8.dp))
                    .background(
                        MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(vertical = 8.dp, horizontal = 16.dp)
            ) {
                Text(
                    style = MaterialTheme.typography.bodyLarge,
                    text = "Accommodation offers"
                )

                accommodationOffers.forEachIndexed { i, offer ->
                    Row(
                        modifier = Modifier
                            .clickable {
                                accommodation = offer
                            }
                            .background(
                                if (offer == accommodation) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    MaterialTheme.colorScheme.primaryContainer
                                }
                            )
                            .padding(vertical = 8.dp, horizontal = 4.dp)
                    ) {
                        Text(
                            style = MaterialTheme.typography.bodyMedium,
                            text = offer.partnerName,
                            color = if (offer == accommodation) {
                                MaterialTheme.colorScheme.onPrimary
                            } else {
                                MaterialTheme.colorScheme.onSurface
                            }
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            style = MaterialTheme.typography.labelLarge,
                            text = offer.pricePerPerson.toString(),
                            color = if (offer == accommodation) {
                                MaterialTheme.colorScheme.onPrimary
                            } else {
                                MaterialTheme.colorScheme.onSurface
                            }
                        )
                    }
                    if (i != accommodationOffers.lastIndex) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(1.dp)
                                .background(Color.LightGray)
                        )
                    }
                }
            }

            Button(
                enabled = transport != null && accommodation != null,
                onClick = {
                    onOffersAccepted(transport!!, accommodation!!)
                }
            ) {
                Text(text = "Accept selected offers")
            }

            Button(
                onClick = {
                    onOffersRejected()
                }
            ) {
                Text(text = "Reject all offers")
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun PartnerOfferReviewScreenPreview() {
    PartnerOfferReviewScreen(
        accommodationOffers = listOf(
            PartnerOfferItem(
                partnerId = 1,
                processKey = 2,
                partnerName = "Flixbus",
                pricePerPerson = 300
            ),
            PartnerOfferItem(
                partnerId = 1,
                processKey = 2,
                partnerName = "Arriva",
                pricePerPerson = 300
            ),
            PartnerOfferItem(
                partnerId = 1,
                processKey = 2,
                partnerName = "Pogon",
                pricePerPerson = 300
            ),
        ),
        transportOffers = listOf(
            PartnerOfferItem(
                partnerId = 1,
                processKey = 2,
                partnerName = "Flixbus",
                pricePerPerson = 300
            ),
            PartnerOfferItem(
                partnerId = 1,
                processKey = 2,
                partnerName = "Arriva",
                pricePerPerson = 300
            ),
            PartnerOfferItem(
                partnerId = 1,
                processKey = 2,
                partnerName = "Pogon",
                pricePerPerson = 300
            ),
        ),
        onOffersAccepted = { _, _ -> },
        onOffersRejected = {},
        onBackPressed = {}
    )
}