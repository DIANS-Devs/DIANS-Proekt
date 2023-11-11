import requests
import csv

#Replace with your Google Maps API key
api_key = "api_key"

# Define the API endpoint for the Google Places API
api_url = 'https://maps.googleapis.com/maps/api/place/nearbysearch/json'

#center point, radius
circles = [[1, "41.962504,21.030960", 24000], #tetovo
           [2, "42.072164,21.611656", 25000], #skopje-kumanovo
           [3, "42.096070,22.123778", 33500], #kratovo
           [4, "41.835580,22.600149", 25000], #vinica
           [5, "41.557553,22.769994", 18500], #berovo
           [6, "41.362954,22.477665", 26500], #valandovo
           [7, "41.343340,21.935466", 26500], #kavadarci
           [8, "41.138260,21.545149", 27500], #mogila
           [9, "41.080494,21.109461", 24000], #bitola-ohrid
           [10, "41.287892,20.850366", 30000], #debarca
           [11, "41.660122,20.827568", 27000], #kicevo
           [12, "41.571552,21.355826", 27000], #mk brod
           [13, "41.719584,21.816110", 27000], #veles
           [14, "41.658671,22.216189", 19000]] #stip-radovis


for (id, center_point, distance) in circles:
    # Define the parameters for the request
    params = {
        'location': center_point,  # Latitude and longitude for Macedonia
        'radius': distance,  # Radius in meters (adjust as needed)
        'keyword': 'winery',  # Keyword to search for wineries
        'key': api_key
    }

    # Make the API request
    response = requests.get(api_url, params=params)

    if response.status_code == 200:
        data = response.json()
        if 'results' in data:
            wineries = data['results']
            #Define the CSV file and fieldnames
            with open(f'wineries_{id}.csv', 'w', newline='', encoding='utf-8') as csvfile:
                fieldnames = ['ID', 'Name', 'Types', 'Address', 'Phone Number', 'International Phone Number', 'Opening hours', 'Website', 'Operational', 'Rating', 'Total Reviewers', 'Reviews', 'Wheelchair Accessible', 'Latitude', 'Longitude']
                writer = csv.DictWriter(csvfile, fieldnames=fieldnames)
                
                writer.writeheader()
                
                for winery in wineries:
                    place_id = winery.get('place_id', 'N/A')
                    operational = winery.get('business_status','N/A')
                    name = winery.get('name', 'N/A')
                    address = winery.get('vicinity', 'N/A')
                    rating = winery.get('rating', 'N/A')
                    types = winery.get('types', [])
                    reviewers = winery.get('user_ratings_total', 'N/A')
                                                        
                    # Access and format location (latitude and longitude)
                    latitude = winery['geometry']['location']['lat']
                    longitude = winery['geometry']['location']['lng']
                    
                    get_place_details = f"https://maps.googleapis.com/maps/api/place/details/json?placeid={place_id}&key={api_key}"

                    response_place_details = requests.get(get_place_details)

                    website = 'N/A'
                    opening_hours = []
                    phone_number = 'N/A'
                    international_phone_number = 'N/A'
                    review_data = []
                    wheelchair_accessible = 'N/A'

                    if response_place_details.status_code == 200:
                        details_data = response_place_details.json()
                        if 'result' in details_data:
                            place_details = details_data['result']


                            if 'current_opening_hours' in place_details:
                                opening_hours = place_details['current_opening_hours']['weekday_text']
                                list_opening_hours = list(opening_hours)

                            phone_number = place_details.get('formatted_phone_number', 'N/A')
                            international_phone_number = place_details.get('international_phone_number', 'N/A')
                            website = place_details.get('website', 'N/A')
                            wheelchair_accessible = place_details.get('wheelchair_accessible_entrance', 'N/A')

                            review_data = []
                            for review in place_details.get('reviews', []):
                                review_data.append({
                                    'review_author': review.get('author_name', 'N/A'),
                                    'rating': str(review.get('rating', 'N/A')),
                                    'text': review.get('text', 'N/A'),
                                    'relative_time_description': review.get('relative_time_description', 'N/A')
                                })
                            review_data = list(review_data)
                        else:
                            print(f'Error: Unable to retrieve place details. Status code: {response_place_details.status_code}')
                    else:
                        print(f'Error: Unable to retrieve place details. Status code: {response_place_details.status_code}')


                    # Write data to the CSV file
                    writer.writerow({
                        'ID': place_id,
                        'Name': name,
                        'Types': types,
                        'Address': address,
                        'Rating': rating,
                        'Phone Number': phone_number,
                        'International Phone Number': international_phone_number,
                        'Opening hours': opening_hours,
                        'Website': website,
                        'Operational': operational,
                        'Total Reviewers': reviewers,
                        'Reviews': review_data,
                        'Wheelchair Accessible': wheelchair_accessible,
                        'Latitude': latitude,
                        'Longitude': longitude
                    })

        else:
            print(f'Error: Unable to retrieve data. Status code: {response.status_code}')
    else:
        print(f'Error: Unable to retrieve data. Status code: {response.status_code}')
