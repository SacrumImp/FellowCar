<?php
    namespace App\Http\Controllers;
	use App\User;
	use Illuminate\Http\Request;
	use Carbon\Carbon;
	use App\Trips;
	use App\Users;

    class ScheduleController extends Controller
    {
        /**
         * Create a new controller instance.
         *
         * @return void
         */
        public function __construct()
        {
            //
        }

		// -------------------------------------------------------------------------------------------------------------
		// -------------------------------------------------------------------------------------------------------------
        // Function: gets the trips of a specific user.
		// Example of input:
		//	{
		//		"id": 1
		//	}
        public function index(Request  $request)
        {
            // Get the trips of a specific user by his id.
            $trips = Trips::where('id', $request->id)->get();
			// Data is turned into an array.
			$trips = json_decode($trips, true);
            // Returns the array of trips.-
			// How many trips are the limit?
            return $trips;
        }
		// ------------------------------------------------------------------------------------------------------------
		// -------------------------------------------------------------------------------------------------------------

		// -------------------------------------------------------------------------------------------------------------
		// -------------------------------------------------------------------------------------------------------------
		// Function: creates a trip.
		// Example of input:
		//	{
		//		"id": 1,
		//		"origin": home,
		//		"destiny": school,
		//      "hour": 9:00
		// }
        public function create(Request $request)
        {
            // The trip gets filled.
            $trip = new Trips;
			$trip->id = $request->id;
            $trip->created_at = Carbon::now();
            $trip->updated_at = Carbon::now();
            $trip->origin = $request->origin;
            $trip->destiny = $request->destiny;
			$trip->hour = $request->hour;
            // The trip is being saved.
            $trip->save();
			// ---------------------------------------------------------------------------------------------------------
			// An exception in case of collapsing trips should be added.
			// ---------------------------------------------------------------------------------------------------------
			// New trips are returned.
			return response()->json($trip);
        }
		// -------------------------------------------------------------------------------------------------------------
		// -------------------------------------------------------------------------------------------------------------


		// -------------------------------------------------------------------------------------------------------------
		// -------------------------------------------------------------------------------------------------------------
		// Example of input:
		//	{
		//	    "user_id": 1,
		//	    "origin": "home",
		//	    "destiny": "girlfriend"
		//	}
        public function update(Request $request)
        {
			// Get the current trip.
            $trip = Trips::where('trip_id', $request->trip_id)->first();
			// Update the trip.
            $trip->origin = $request->input('origin');
			$trip->destiny = $request->input('destiny');
			$trip->hour = $request->input('hour');
			// Save the trip.
            $trip->save();
			// Return the updated trip info.
            return response()->json($trip);
        }
		// -------------------------------------------------------------------------------------------------------------
		// -------------------------------------------------------------------------------------------------------------
    }
