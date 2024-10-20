package health_content;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pethub.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MentalHealthEnrichment_kit_essentials#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MentalHealthEnrichment_kit_essentials extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MentalHealthEnrichment_kit_essentials() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MentalHealthEnrichment_kit_essentials.
     */
    // TODO: Rename and change types and number of parameters
    public static MentalHealthEnrichment_kit_essentials newInstance(String param1, String param2) {
        MentalHealthEnrichment_kit_essentials fragment = new MentalHealthEnrichment_kit_essentials();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mental_health_enrichment_kit_essentials, container, false);
    }
}