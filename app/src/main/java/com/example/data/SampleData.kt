package com.example.data

import com.example.model.*

object SampleData {

    val initialDays: List<DayItinerary> = listOf(
        DayItinerary(
            dayNumber = 1,
            title = "North Goa & Aguada",
            subtitle = "Lighthouse ramparts, coastline strolls & seafood dinner",
            activities = listOf(
                ActivityItem(
                    id = "act-1-1",
                    time = "09:30 AM",
                    duration = "2h 00m",
                    title = "Fort Aguada & 17th Century Lighthouse",
                    locationName = "Sinquerim, Candolim",
                    costInr = 150,
                    categoryTag = "Heritage",
                    imageUrl = "https://images.unsplash.com/photo-1512343879784-a960bf40e7f2?w=800&auto=format&fit=crop&q=80",
                    transitToNext = TransitInfo(
                        durationText = "12 min drive (4.2 km)",
                        distanceText = "4.2 km",
                        routeNote = "via Fort Aguada Rd towards Candolim",
                        mode = "drive"
                    ),
                    whyVisit = "Panoramic Arabian Sea overlook, Portuguese coastal artillery walls, and Asia's oldest freshwater reservoir fort.",
                    bestTime = "09:00 AM - 11:30 AM (Cool ocean breeze before noon sun)",
                    entryFee = "₹50 Indian Citizens · ₹300 Foreigners · Free Parking",
                    nearbyGems = listOf("Sinquerim Secret Cove", "Lower Aguada Bastion", "Taj Village Viewpoint"),
                    lat = 15.4925f,
                    lng = 73.7738f
                ),
                ActivityItem(
                    id = "act-1-2",
                    time = "12:00 PM",
                    duration = "1h 45m",
                    title = "Candolim Dunes & Coastal Shack Lunch",
                    locationName = "Candolim Beach Road",
                    costInr = 1200,
                    categoryTag = "Beach & Food",
                    imageUrl = "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?w=800&auto=format&fit=crop&q=80",
                    transitToNext = TransitInfo(
                        durationText = "18 min drive (6.8 km)",
                        distanceText = "6.8 km",
                        routeNote = "via Aguada-Siolim Rd to Calangute-Nerul bridge",
                        mode = "drive"
                    ),
                    whyVisit = "Golden sand stretches with quieter beachside bamboo pavilions serving tender butter garlic prawns and fresh sol kadhi.",
                    bestTime = "Midday sheltered patio seating with cool sea winds",
                    entryFee = "Free entry · Meals ~₹400/person",
                    nearbyGems = listOf("Candolim Flea Lane", "Dr. Jack Sequeira Statue", "Nerul River Backwaters"),
                    lat = 15.5178f,
                    lng = 73.7628f
                ),
                ActivityItem(
                    id = "act-1-3",
                    time = "02:30 PM",
                    duration = "2h 15m",
                    title = "Fisherman's Wharf Goan Feast",
                    locationName = "Nerul Waterfront",
                    costInr = 1650,
                    categoryTag = "Culinary",
                    imageUrl = "https://images.unsplash.com/photo-1555396273-367ea4eb4db5?w=800&auto=format&fit=crop&q=80",
                    transitToNext = TransitInfo(
                        durationText = "15 min drive (5.5 km)",
                        distanceText = "5.5 km",
                        routeNote = "via Sinquerim Cliffside road",
                        mode = "drive"
                    ),
                    whyVisit = "Authentic riverfront dining serving claypot fish curry, crab xec-xec, and wood-fired poi bread.",
                    bestTime = "Late afternoon riverside breezes",
                    entryFee = "No entry fee · A la carte dining",
                    nearbyGems = listOf("Coco Beach jetty", "Nerul Church", "Reis Magos Fort"),
                    lat = 15.5050f,
                    lng = 73.7850f
                ),
                ActivityItem(
                    id = "act-1-4",
                    time = "05:30 PM",
                    duration = "1h 45m",
                    title = "Golden Hour at Sinquerim Sunset Point",
                    locationName = "Sinquerim Cliffs",
                    costInr = 0,
                    categoryTag = "Sunset & Views",
                    imageUrl = "https://images.unsplash.com/photo-1518684079-3c830dcef090?w=800&auto=format&fit=crop&q=80",
                    transitToNext = null,
                    whyVisit = "Unobstructed crimson horizon where dramatic lateralite cliffs plunge into the Arabian surf.",
                    bestTime = "05:15 PM - 06:45 PM for magical light",
                    entryFee = "Completely free open cliff walk",
                    nearbyGems = listOf("Sinquerim Water Sports Jetty", "Aguada Helipad deck"),
                    lat = 15.4950f,
                    lng = 73.7680f
                )
            )
        ),
        DayItinerary(
            dayNumber = 2,
            title = "Old Goa & Heritage Latin Quarter",
            subtitle = "Baroque basilicas, pastel villas of Fontainhas & Goan sweets",
            activities = listOf(
                ActivityItem(
                    id = "act-2-1",
                    time = "09:00 AM",
                    duration = "2h 00m",
                    title = "Basilica of Bom Jesus & Sé Cathedral",
                    locationName = "Old Goa (Velha Goa)",
                    costInr = 100,
                    categoryTag = "Heritage",
                    imageUrl = "https://images.unsplash.com/photo-1596178065887-1198b6148b2b?w=800&auto=format&fit=crop&q=80",
                    transitToNext = TransitInfo(
                        durationText = "20 min drive (10.5 km)",
                        distanceText = "10.5 km",
                        routeNote = "via Panaji-Old Goa Bypass Road",
                        mode = "drive"
                    ),
                    whyVisit = "UNESCO World Heritage Baroque masterpiece housing the sacred relics of St. Francis Xavier.",
                    bestTime = "09:00 AM before pilgrim bus tours arrive",
                    entryFee = "Free entry · Museum ticket ₹20",
                    nearbyGems = listOf("Church of St. Cajetan", "Arch of the Viceroys", "Church of St. Francis of Assisi"),
                    lat = 15.5009f,
                    lng = 73.9116f
                ),
                ActivityItem(
                    id = "act-2-2",
                    time = "11:30 AM",
                    duration = "2h 30m",
                    title = "Fontainhas Latin Quarter Walking Tour",
                    locationName = "Fontainhas, Panaji",
                    costInr = 450,
                    categoryTag = "Culture & Walk",
                    imageUrl = "https://images.unsplash.com/photo-1582510003544-4d00b7f74220?w=800&auto=format&fit=crop&q=80",
                    transitToNext = TransitInfo(
                        durationText = "5 min walk (300 m)",
                        distanceText = "300 m",
                        routeNote = "via 31st January Road cobbled lane",
                        mode = "walk"
                    ),
                    whyVisit = "Narrow cobblestone alleys lined with vibrant Portuguese-era villas painted in ochre yellow, indigo, and terracotta.",
                    bestTime = "11:00 AM - 01:30 PM (Great for architectural photography)",
                    entryFee = "Free street walk · Guided map available",
                    nearbyGems = listOf("St. Sebastian Chapel", "Gitanjali Art Gallery", "Confeitaria 31 de Janeiro"),
                    lat = 15.4989f,
                    lng = 73.8315f
                ),
                ActivityItem(
                    id = "act-2-3",
                    time = "02:00 PM",
                    duration = "1h 30m",
                    title = "Viva Panjim Heritage Bistro",
                    locationName = "Fontainhas, Panjim",
                    costInr = 950,
                    categoryTag = "Culinary",
                    imageUrl = "https://images.unsplash.com/photo-1544025162-d76694265947?w=800&auto=format&fit=crop&q=80",
                    transitToNext = TransitInfo(
                        durationText = "15 min drive (4.5 km)",
                        distanceText = "4.5 km",
                        routeNote = "via Dayanand Bandodkar Marg",
                        mode = "drive"
                    ),
                    whyVisit = "Award-winning cozy dining in a 150-year-old Indo-Portuguese ancestral home famous for Chicken Xacuti and Bebinca.",
                    bestTime = "02:00 PM lunch seating",
                    entryFee = "No entry · Traditional homestyle menu",
                    nearbyGems = listOf("Panaji Church Steps", "Mandovi River Promenade"),
                    lat = 15.4975f,
                    lng = 73.8300f
                ),
                ActivityItem(
                    id = "act-2-4",
                    time = "05:00 PM",
                    duration = "2h 00m",
                    title = "Miramar Beach & Mandovi Estuary Sunset",
                    locationName = "Miramar, Panaji",
                    costInr = 200,
                    categoryTag = "Sunset",
                    imageUrl = "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?w=800&auto=format&fit=crop&q=80",
                    transitToNext = null,
                    whyVisit = "Where the Mandovi river unites with the open Arabian sea; palm-shaded promenade with roasted spiced corn and cane juice.",
                    bestTime = "05:00 PM - 07:00 PM",
                    entryFee = "Free beach access",
                    nearbyGems = listOf("Dona Paula Viewpoint", "Goa Science Centre"),
                    lat = 15.4830f,
                    lng = 73.8050f
                )
            )
        ),
        DayItinerary(
            dayNumber = 3,
            title = "South Goa Serenity",
            subtitle = "Crescent bays, secluded lagoon kayaking & Martin's Corner",
            activities = listOf(
                ActivityItem(
                    id = "act-3-1",
                    time = "09:30 AM",
                    duration = "2h 30m",
                    title = "Palolem Beach Crescent & Butterfly Bay",
                    locationName = "Canacona, South Goa",
                    costInr = 600,
                    categoryTag = "Beach & Boat",
                    imageUrl = "https://images.unsplash.com/photo-1544644181-1484b3fdfc62?w=800&auto=format&fit=crop&q=80",
                    transitToNext = TransitInfo(
                        durationText = "22 min drive (11 km)",
                        distanceText = "11 km",
                        routeNote = "via coastal bypass to Cola Lagoon",
                        mode = "drive"
                    ),
                    whyVisit = "Gentle turquoise water hugged by dense coconut palm groves and serene dolphin sighting boat rides.",
                    bestTime = "Morning calm waters",
                    entryFee = "Boat ride ~₹300/person",
                    nearbyGems = listOf("Monkey Island", "Canacona High Street", "Patnem Beach"),
                    lat = 15.0100f,
                    lng = 74.0231f
                ),
                ActivityItem(
                    id = "act-3-2",
                    time = "12:30 PM",
                    duration = "2h 00m",
                    title = "Kayaking at Hidden Cola Beach Lagoon",
                    locationName = "Cola Village, South Goa",
                    costInr = 750,
                    categoryTag = "Adventure",
                    imageUrl = "https://images.unsplash.com/photo-1544551763-46a013bb70d5?w=800&auto=format&fit=crop&q=80",
                    transitToNext = TransitInfo(
                        durationText = "35 min drive (18 km)",
                        distanceText = "18 km",
                        routeNote = "via NH66 heading North toward Betalbatim",
                        mode = "drive"
                    ),
                    whyVisit = "A rare emerald freshwater lagoon separated from the raging sea breakers by a razor-thin golden sandbar.",
                    bestTime = "12:30 PM - 02:30 PM under shaded emerald canopy",
                    entryFee = "Kayak rental ~₹250/hour",
                    nearbyGems = listOf("Agonda Turtle Sanctuary", "Cabo de Rama Fort"),
                    lat = 15.0560f,
                    lng = 73.9740f
                ),
                ActivityItem(
                    id = "act-3-3",
                    time = "03:30 PM",
                    duration = "2h 00m",
                    title = "Martin's Corner Iconic Lunch",
                    locationName = "Betalbatim, Salcete",
                    costInr = 1800,
                    categoryTag = "Culinary",
                    imageUrl = "https://images.unsplash.com/photo-1517248135467-4c7edcad34c4?w=800&auto=format&fit=crop&q=80",
                    transitToNext = null,
                    whyVisit = "Legendary culinary sanctuary beloved by world cricketers and artists for butter garlic lobster and kingfish rava fry.",
                    bestTime = "Late afternoon leisure lunch",
                    entryFee = "No entry fee · Live acoustic music on weekends",
                    nearbyGems = listOf("Sunset Beach Betalbatim", "Majorda Beach"),
                    lat = 15.3020f,
                    lng = 73.9180f
                )
            )
        ),
        DayItinerary(
            dayNumber = 4,
            title = "Spice Plantations & Waterfall Trail",
            subtitle = "Aromatic cardamom hills, elephant baths & heritage distillery",
            activities = listOf(
                ActivityItem(
                    id = "act-4-1",
                    time = "10:00 AM",
                    duration = "3h 00m",
                    title = "Sahakari Spice Farm Guided Trail",
                    locationName = "Curti, Ponda",
                    costInr = 1200,
                    categoryTag = "Nature & Farm",
                    imageUrl = "https://images.unsplash.com/photo-1540555700478-4be289fbecef?w=800&auto=format&fit=crop&q=80",
                    transitToNext = TransitInfo(
                        durationText = "15 min drive (7 km)",
                        distanceText = "7 km",
                        routeNote = "via Ponda-Belgaum Highway",
                        mode = "drive"
                    ),
                    whyVisit = "Traditional marigold garland welcome, vanilla and pepper canopy walks, and a grand buffet served on fresh banana leaves.",
                    bestTime = "10:00 AM - 01:00 PM",
                    entryFee = "₹500 includes guided tour + farm feast buffet",
                    nearbyGems = listOf("Tropical Spice Farm", "Safaa Masjid", "Mangueshi Temple"),
                    lat = 15.4050f,
                    lng = 74.0200f
                ),
                ActivityItem(
                    id = "act-4-2",
                    time = "02:00 PM",
                    duration = "2h 30m",
                    title = "Local Artisanal Cashew Feni Tasting",
                    locationName = "Ponda Foothills",
                    costInr = 850,
                    categoryTag = "Culinary & Craft",
                    imageUrl = "https://images.unsplash.com/photo-1510812431401-41d2bd2722f3?w=800&auto=format&fit=crop&q=80",
                    transitToNext = null,
                    whyVisit = "Learn the century-old copper pot distillation process of GI-tagged Goan Cashew Feni paired with roasted spiced cashews.",
                    bestTime = "02:00 PM - 04:30 PM",
                    entryFee = "Tasting experience ~₹350/person",
                    nearbyGems = listOf("Khandepar Rock Cut Caves", "Bondla Wildlife Sanctuary"),
                    lat = 15.4120f,
                    lng = 74.0450f
                )
            )
        ),
        DayItinerary(
            dayNumber = 5,
            title = "Flea Markets & Departure",
            subtitle = "Vintage vinyls, hand-woven cottons & cliffside farewell",
            activities = listOf(
                ActivityItem(
                    id = "act-5-1",
                    time = "10:00 AM",
                    duration = "2h 30m",
                    title = "Anjuna Beach Flea Market & Curios",
                    locationName = "Anjuna Coastline",
                    costInr = 800,
                    categoryTag = "Market & Shopping",
                    imageUrl = "https://images.unsplash.com/photo-1533900298318-6b8da08a523e?w=800&auto=format&fit=crop&q=80",
                    transitToNext = TransitInfo(
                        durationText = "10 min walk (600 m)",
                        distanceText = "600 m",
                        routeNote = "along Anjuna cliff pathway",
                        mode = "walk"
                    ),
                    whyVisit = "World-famous bohemian bazaar filled with silver jewelry, spices, Tibetan prayer bowls, and artisanal leather shoes.",
                    bestTime = "Morning breeze before the midday heat",
                    entryFee = "Free entry · Cash recommended",
                    nearbyGems = listOf("Curlies Beach Shack", "Shiva Valley", "Little Vagator Beach"),
                    lat = 15.5780f,
                    lng = 73.7420f
                ),
                ActivityItem(
                    id = "act-5-2",
                    time = "01:00 PM",
                    duration = "2h 00m",
                    title = "Curlies Cliffside Brunch & Farewell",
                    locationName = "South Anjuna Beach",
                    costInr = 1450,
                    categoryTag = "Culinary",
                    imageUrl = "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?w=800&auto=format&fit=crop&q=80",
                    transitToNext = null,
                    whyVisit = "Iconic multi-tier wooden deck perched over breaking waves, fresh wood-fired thin-crust pizza and iced coconut lattes.",
                    bestTime = "01:00 PM - 03:00 PM",
                    entryFee = "No cover fee · Dining terrace",
                    nearbyGems = listOf("Anjuna Rocky Cove", "German Bakery"),
                    lat = 15.5740f,
                    lng = 73.7405f
                )
            )
        )
    )

    val budgetCategories = listOf(
        BudgetCategory("Stay", 8000, "hotel", 0xFF1B4332),
        BudgetCategory("Transport", 5200, "directions_car", 0xFFD96B43),
        BudgetCategory("Food", 4800, "restaurant", 0xFFE07A5F),
        BudgetCategory("Activities", 3200, "confirmation_number", 0xFFE9C46A),
        BudgetCategory("Buffer", 2280, "savings", 0xFF52B788)
    )

    val initialSuggestions = listOf(
        OptimizationSuggestion(
            id = "opt-1",
            title = "Swap 2 private cabs for rented scooters",
            description = "Save on Day 1 & Day 2 local transit by renting 2 Activas in Candolim instead of chauffeur sedans.",
            savingsInr = 1400,
            category = "Transport",
            isApplied = false
        ),
        OptimizationSuggestion(
            id = "opt-2",
            title = "Replace paid beach club with free Sinquerim sunset deck",
            description = "Swap commercial club entry for the elevated Sinquerim lateralite cliff promenade with better panoramic views.",
            savingsInr = 1200,
            category = "Activities",
            isApplied = false
        ),
        OptimizationSuggestion(
            id = "opt-3",
            title = "Book spice farm direct entry bundle",
            description = "Pre-booking the Sahakari farm package includes the full authentic Goan buffet lunch at ₹200 off per person.",
            savingsInr = 600,
            category = "Food",
            isApplied = false
        )
    )

    val destinations = listOf(
        DestinationHighlight(
            id = "dest-goa",
            name = "Goa",
            state = "Western Coast",
            tagline = "Portuguese Baroque cathedrals, emerald lagoons & spice aromas",
            bucket = "Untouched Coastal Sanctuaries",
            bestSeason = "Oct - Mar",
            budgetEstimate = "₹18,000 - ₹25,000",
            travelTime = "1h 10m flight from Mumbai",
            tags = listOf("Coastal", "Heritage", "Culinary", "Nightlife"),
            imageUrl = "https://images.unsplash.com/photo-1512343879784-a960bf40e7f2?w=800&auto=format&fit=crop&q=80",
            description = "A sunlit paradise where Latin heritage, secluded river estuaries, and vibrant seafood traditions converge."
        ),
        DestinationHighlight(
            id = "dest-jaipur",
            name = "Jaipur",
            state = "Rajasthan",
            tagline = "Pink sandstone ramparts, royal havelis & jeweled bazaars",
            bucket = "Weekend Escapes Under ₹10,000",
            bestSeason = "Oct - Feb",
            budgetEstimate = "₹9,500 - ₹14,000",
            travelTime = "4h 30m expressway from Delhi",
            tags = listOf("Under ₹10k", "Heritage", "Architecture", "Shopping"),
            imageUrl = "https://images.unsplash.com/photo-1603288940316-24e5ef952f40?w=800&auto=format&fit=crop&q=80",
            description = "The majestic Pink City brimming with Amer fort ramparts, Hawa Mahal balconies, and ghewar sweets."
        ),
        DestinationHighlight(
            id = "dest-kerala",
            name = "Kerala (Munnar & Alleppey)",
            state = "God's Own Country",
            tagline = "Misty tea slopes, tranquil backwater houseboats & spices",
            bucket = "Untouched Coastal Sanctuaries",
            bestSeason = "Sep - Mar",
            budgetEstimate = "₹22,000 - ₹28,000",
            travelTime = "Direct flight to Kochi + 3h drive",
            tags = listOf("Nature", "Backwaters", "Culinary", "Ayurveda"),
            imageUrl = "https://images.unsplash.com/photo-1602216056096-3b40cc0c9944?w=800&auto=format&fit=crop&q=80",
            description = "Glide through emerald canals fringed with swaying palms and hike crisp rolling tea plantations."
        ),
        DestinationHighlight(
            id = "dest-udaipur",
            name = "Udaipur",
            state = "Rajasthan",
            tagline = "City of Lakes, marble palaces & crimson sunset ghats",
            bucket = "Weekend Escapes Under ₹10,000",
            bestSeason = "Oct - Mar",
            budgetEstimate = "₹12,000 - ₹18,000",
            travelTime = "1h 20m flight from Mumbai/Delhi",
            tags = listOf("Under ₹10k", "Romantic", "Lakes", "Heritage"),
            imageUrl = "https://images.unsplash.com/photo-1615836245337-f5b9b2303f10?w=800&auto=format&fit=crop&q=80",
            description = "Lake Pichola reflections, grand City Palace courtyards, and rooftop Mewari feasts."
        ),
        DestinationHighlight(
            id = "dest-manali",
            name = "Manali & Solang",
            state = "Himachal Pradesh",
            tagline = "Pine-forested valleys, glacial rivers & apple orchards",
            bucket = "Weekend Escapes Under ₹10,000",
            bestSeason = "Nov - Jun",
            budgetEstimate = "₹8,500 - ₹13,000",
            travelTime = "Overnight Volvo from Delhi / Chandigarh",
            tags = listOf("Under ₹10k", "Mountains", "Adventure", "Snow"),
            imageUrl = "https://images.unsplash.com/photo-1626621341517-bbf3d9990a23?w=800&auto=format&fit=crop&q=80",
            description = "Crisp cedar scented air, old Himalayan wooden houses, cafe trails, and soaring mountain passes."
        ),
        DestinationHighlight(
            id = "dest-mumbai",
            name = "Mumbai",
            state = "Maharashtra",
            tagline = "Art Deco promenades, Parsi cafes & coastal glamour",
            bucket = "Culinary Pilgrimages",
            bestSeason = "Nov - Feb",
            budgetEstimate = "₹15,000 - ₹22,000",
            travelTime = "Major Transit Hub",
            tags = listOf("Culinary", "Art Deco", "Culture", "Fast-Paced"),
            imageUrl = "https://images.unsplash.com/photo-1570168007204-dfb528c6958f?w=800&auto=format&fit=crop&q=80",
            description = "Marine Drive's Queen's Necklace, Irani chai with bun maska, and the buzzing Kala Ghoda art district."
        ),
        DestinationHighlight(
            id = "dest-meghalaya",
            name = "Meghalaya",
            state = "Northeast India",
            tagline = "Living root bridges, crystalline rivers & cloud canyons",
            bucket = "Untouched Coastal Sanctuaries",
            bestSeason = "Oct - Apr",
            budgetEstimate = "₹20,000 - ₹26,000",
            travelTime = "Flight to Guwahati + scenic 3h drive",
            tags = listOf("Living Bridges", "Waterfalls", "Caves", "Nature"),
            imageUrl = "https://images.unsplash.com/photo-1544644181-1484b3fdfc62?w=800&auto=format&fit=crop&q=80",
            description = "The abode of clouds featuring Cherrapunji's double decker root bridges and Dawki's transparent waters."
        ),
        DestinationHighlight(
            id = "dest-hampi",
            name = "Hampi",
            state = "Karnataka",
            tagline = "Surreal boulder landscapes & 14th century Vijayanagara ruins",
            bucket = "Weekend Escapes Under ₹10,000",
            bestSeason = "Oct - Mar",
            budgetEstimate = "₹7,500 - ₹11,000",
            travelTime = "Overnight train / bus from Bengaluru",
            tags = listOf("Under ₹10k", "Ancient Ruins", "Boulders", "UNESCO"),
            imageUrl = "https://images.unsplash.com/photo-1600100397608-f010f4460775?w=800&auto=format&fit=crop&q=80",
            description = "Cycle through stone chariots, monumental Virupaksha temple, and watch golden sunsets across boulder hills."
        )
    )

    val savedTrips = listOf(
        SavedTripItem(
            id = "trip-active",
            title = "Goa Coastal Drift",
            destination = "Goa, India",
            dates = "Oct 12 - Oct 17, 2026",
            companions = "3 Travellers (2 Adults, 1 Child)",
            spentInr = 23480,
            targetInr = 25000,
            status = "Upcoming",
            imageUrl = "https://images.unsplash.com/photo-1512343879784-a960bf40e7f2?w=800&auto=format&fit=crop&q=80"
        ),
        SavedTripItem(
            id = "trip-draft-1",
            title = "Jaipur Pink Citadel Tour",
            destination = "Jaipur, Rajasthan",
            dates = "Nov 04 - Nov 07, 2026",
            companions = "2 Travellers (Couple)",
            spentInr = 8400,
            targetInr = 12000,
            status = "Drafts",
            imageUrl = "https://images.unsplash.com/photo-1603288940316-24e5ef952f40?w=800&auto=format&fit=crop&q=80"
        ),
        SavedTripItem(
            id = "trip-draft-2",
            title = "Hampi Stone Chariot Cycling",
            destination = "Hampi, Karnataka",
            dates = "Dec 18 - Dec 21, 2026",
            companions = "Solo Explorer",
            spentInr = 5800,
            targetInr = 9000,
            status = "Drafts",
            imageUrl = "https://images.unsplash.com/photo-1600100397608-f010f4460775?w=800&auto=format&fit=crop&q=80"
        ),
        SavedTripItem(
            id = "trip-comp-1",
            title = "Munnar Misty Ridge Retreat",
            destination = "Munnar, Kerala",
            dates = "Jan 14 - Jan 18, 2026",
            companions = "4 Friends",
            spentInr = 24600,
            targetInr = 25000,
            status = "Completed",
            imageUrl = "https://images.unsplash.com/photo-1602216056096-3b40cc0c9944?w=800&auto=format&fit=crop&q=80"
        ),
        SavedTripItem(
            id = "trip-comp-2",
            title = "Udaipur Royal Lakeside",
            destination = "Udaipur, Rajasthan",
            dates = "Feb 20 - Feb 23, 2026",
            companions = "2 Travellers",
            spentInr = 14200,
            targetInr = 15000,
            status = "Completed",
            imageUrl = "https://images.unsplash.com/photo-1615836245337-f5b9b2303f10?w=800&auto=format&fit=crop&q=80"
        ),
        SavedTripItem(
            id = "trip-comp-3",
            title = "Manali Cedar & Apple Blossom",
            destination = "Manali, Himachal",
            dates = "Apr 02 - Apr 07, 2026",
            companions = "3 Travellers",
            spentInr = 18900,
            targetInr = 20000,
            status = "Completed",
            imageUrl = "https://images.unsplash.com/photo-1626621341517-bbf3d9990a23?w=800&auto=format&fit=crop&q=80"
        )
    )
}
