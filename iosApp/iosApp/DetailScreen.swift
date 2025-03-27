import SwiftUI
import ComposeApp

struct DetailScreen: View {
    
    private var vm: DetailViewModel
    
    init(movieId: Int32) {
        vm = DetailViewModel(movieId: movieId)
    }
    
    var body: some View {
        Observing(vm.state) { state in
            if let title = state.movie?.title {
                Text(title)
            }
        }
    }
}
